package com.plynko.monitoring;

import com.plynko.model.UrlConfig;
import com.plynko.model.State;
import com.plynko.model.Status;
import com.plynko.repository.ConfigRepository;
import com.plynko.repository.StateRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UrlMonitoringTask implements Runnable {

    private final List<String> warnings = new ArrayList<>();
    private final List<String> criticals = new ArrayList<>();
    private final List<String> unknown = new ArrayList<>();
    private final List<String> pending = new ArrayList<>();

    private final Integer configId;
    private final ConfigRepository configRepository;
    private final StateRepository stateRepository;

    public UrlMonitoringTask(Integer configId, ConfigRepository configRepository, StateRepository stateRepository) {
        this.configId = configId;
        this.configRepository = configRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public void run() {
        UrlConfig urlConfig = configRepository.get(configId);
        int timeout = (int) urlConfig.getCriticalTime() + 1000;

        if (urlConfig.isActive()) {
            try {
                long startTime = System.nanoTime();

                URL url = new URL(urlConfig.getUrl());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(timeout);
                connection.setReadTimeout(timeout);
                connection.connect();
                String content = new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();

                long responseTime = (System.nanoTime() - startTime) / 1_000_000;
                checkResponseTime(responseTime, urlConfig.getWarningTime(), urlConfig.getCriticalTime());

                int responseCode = connection.getResponseCode();
                checkResponseCode(responseCode, urlConfig.getResponseCode());

                int responseSize = content.getBytes("UTF-8").length;
                checkResponseSize(responseSize, urlConfig.getMinResponseSize(), urlConfig.getMaxResponseSize());

                checkSubstring(content, urlConfig.getSubString());
            } catch (SocketTimeoutException e) {
                criticals.add(String.format("response time: > %d ms (limits: %d/%d ms)", timeout, urlConfig.getWarningTime(), urlConfig.getCriticalTime()));
            } catch (IOException e) {
                unknown.add("monitoring failed");
            }
        } else {
            pending.add("was excluded from monitoring");
        }

        saveState(urlConfig.getUrl());
        cleanInformation();
    }

    private void checkResponseTime(long responseTime, long warningTime, long criticalTime) {
        String info = String.format("response time: %d ms (limits: %d/%d ms)", responseTime, warningTime, criticalTime);

        if (responseTime >= criticalTime) {
            criticals.add(info);
        } else if (responseTime >= warningTime) {
            warnings.add(info);
        }
    }

    private void checkResponseCode(int actualCode, int expectedCode) {
        if (actualCode != expectedCode) {
            String info = String.format("response code: %d (expected: %d)", actualCode, expectedCode);
            criticals.add(info);
        }
    }

    private void checkResponseSize(int responseSize, int minResponseSize, int maxResponseSize) {
        if (responseSize < minResponseSize || responseSize > maxResponseSize) {
            String info = String.format("response size: %d (limits: %d-%d b)", responseSize, minResponseSize, maxResponseSize);
            criticals.add(info);
        }
    }

    private void checkSubstring(String content, String subString) {
        if (subString == null || subString.isEmpty()) {
            return;
        }

        if (!content.contains(subString)) {
            String info = "substring is %s (\"" + subString + "\")";
            criticals.add(String.format(info, "absent"));
        }
    }

    private void saveState(String url) {
        List<String> actualInformation;
        Status status;
        if (!pending.isEmpty()) {
            actualInformation = pending;
            status = Status.PENDING;
        } else if (!unknown.isEmpty()) {
            actualInformation = unknown;
            status = Status.UNKNOWN;
        } else if (!criticals.isEmpty()) {
            actualInformation = criticals;
            status = Status.CRITICAL;
        } else if (!warnings.isEmpty()) {
            actualInformation = warnings;
            status = Status.WARNING;
        } else {
            actualInformation = Collections.emptyList();
            status = Status.OK;
        }

        String information = actualInformation.stream().collect(Collectors.joining("; "));

        stateRepository.save(new State(null, configId, url, status, information));
    }

    private void cleanInformation() {
        warnings.clear();
        criticals.clear();
        unknown.clear();
        pending.clear();
    }
}
