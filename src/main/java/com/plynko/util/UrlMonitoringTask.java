package com.plynko.util;

import com.plynko.model.UrlConfig;
import com.plynko.model.State;
import com.plynko.model.Status;
import com.plynko.repository.InMemoryConfigRepositoryImpl;
import com.plynko.repository.InMemoryStateRepositoryImpl;
import com.plynko.repository.ConfigRepository;
import com.plynko.repository.StateRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UrlMonitoringTask implements Runnable {

    private List<String> oks = new ArrayList<>();
    private List<String> warnings = new ArrayList<>();
    private List<String> criticals = new ArrayList<>();
    private List<String> unknown = new ArrayList<>();
    private List<String> pending = new ArrayList<>();

    private Integer id;

    public UrlMonitoringTask(Integer id) {
        this.id = id;
    }

    @Override
    public void run() {
        ConfigRepository configRepository = InMemoryConfigRepositoryImpl.getInstance();
        UrlConfig urlConfig = configRepository.get(id);

        if (urlConfig.isActive()) {
            try {
                long startTime = System.nanoTime();
                String content = new Scanner(urlConfig.getUrl().openStream(), "UTF-8").useDelimiter("\\A").next();
                long responseTime = (System.nanoTime() - startTime) / 1_000_000;
                analyzeResponseTime(responseTime, urlConfig.getWarningTime(), urlConfig.getCriticalTime());
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
        } else {
            oks.add(info);
        }
    }

    private void saveState(URL url) {
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
            actualInformation = oks;
            status = Status.OK;
        }

        String information = actualInformation.stream().collect(Collectors.joining("; "));

        StateRepository stateRepository = InMemoryStateRepositoryImpl.getInstance();
        stateRepository.save(new State(null, id, url.toString(), status, information));
    }

    private void cleanInformation() {
        oks.clear();
        warnings.clear();
        criticals.clear();
        unknown.clear();
        pending.clear();
    }
}
