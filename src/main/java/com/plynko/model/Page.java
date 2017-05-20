package com.plynko.model;

import java.net.URL;

public class Page {
    private Integer id;

    private URL url;

    private int monitoringPeriod;

    private int warningTime;

    private int criticalTime;

    private int responseCode;

    private int minResponseSize;

    private int maxResponseSize;

    private String subString;

    private boolean active = true;

    public Page(URL url, int monitoringPeriod, int warningTime, int criticalTime, int responseCode,
                int minResponseSize, int maxResponseSize, String subString) {
        this.url = url;
        this.monitoringPeriod = monitoringPeriod;
        this.warningTime = warningTime;
        this.criticalTime = criticalTime;
        this.responseCode = responseCode;
        this.minResponseSize = minResponseSize;
        this.maxResponseSize = maxResponseSize;
        this.subString = subString;
    }
}
