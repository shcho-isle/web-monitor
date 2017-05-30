package com.plynko.util;

import com.plynko.model.UrlConfig;
import com.plynko.model.State;
import com.plynko.model.Status;
import com.plynko.repository.InMemoryConfigRepositoryImpl;
import com.plynko.repository.InMemoryStateRepositoryImpl;
import com.plynko.repository.ConfigRepository;
import com.plynko.repository.StateRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UrlMonitoringTask implements Runnable {

    private List<String> warnings = new ArrayList<>();
    private List<String> criticals = new ArrayList<>();
    private List<String> unknown = new ArrayList<>();
    private List<String> pending = new ArrayList<>();

    private Integer configId;

    public UrlMonitoringTask(Integer configId) {
        this.configId = configId;
    }

    @Override
    public void run() {
        ConfigRepository configRepository = InMemoryConfigRepositoryImpl.getInstance();
        UrlConfig urlConfig = configRepository.get(configId);

        if (urlConfig.isActive()) {
            try {
                long startTime = System.nanoTime();

                URL url = new URL(urlConfig.getUrl());
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                String content = new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();

                long responseTime = (System.nanoTime() - startTime) / 1_000_000;
                analyzeResponseTime(responseTime, urlConfig.getWarningTime(), urlConfig.getCriticalTime());

                int responseCode = connection.getResponseCode();
                analyzeResponseCode(responseCode, urlConfig.getResponseCode());

                int responseSize = content.getBytes("UTF-8").length;
                analyzeResponseSize(responseSize, urlConfig.getMinResponseSize(), urlConfig.getMaxResponseSize());

                analyzeSubstring(content, urlConfig.getSubString());
            } catch (IOException e) {
                unknown.add("monitoring failed");
            }
        } else {
            pending.add("was excluded from monitoring");
        }

        saveState(urlConfig.getUrl());
        cleanInformation();
    }

    private void analyzeResponseTime(long responseTime, long warningTime, long criticalTime) {
        String info = String.format("response time: %d ms (limits: %d/%d ms)", responseTime, warningTime, criticalTime);

        if (responseTime >= criticalTime) {
            criticals.add(info);
        } else if (responseTime >= warningTime) {
            warnings.add(info);
        }
    }

    private void analyzeResponseCode(int actualCode, int expectedCode) {
        String info = String.format("response code: %d (expected: %d)", actualCode, expectedCode);

        if (actualCode != expectedCode) {
            criticals.add(info);
        }
    }

    private void analyzeResponseSize(int responseSize, int minResponseSize, int maxResponseSize) {
        String info = String.format("response size: %d (limits: %d-%d b)", responseSize, minResponseSize, maxResponseSize);

        if (responseSize < minResponseSize || responseSize > maxResponseSize) {
            criticals.add(info);
        }
    }

    private void analyzeSubstring(String content, String subString) {
        if (subString == null || subString.isEmpty()) {
            return;
        }

        String info = "substring is %s (\"" + subString + "\")";

        if (!content.contains(subString)) {
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

        StateRepository stateRepository = InMemoryStateRepositoryImpl.getInstance();
        stateRepository.save(new State(null, configId, url, status, information));
    }

    private void cleanInformation() {
        warnings.clear();
        criticals.clear();
        unknown.clear();
        pending.clear();
    }
}
