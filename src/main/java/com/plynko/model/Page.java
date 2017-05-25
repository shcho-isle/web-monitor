package com.plynko.model;

import java.net.URL;

public class Page extends BaseEntity {

    private URL url;

    // in seconds
    private int monitoringPeriod;

    // in milliseconds
    private long warningTime;

    // in milliseconds
    private long criticalTime;

    private int responseCode;

    // in bytes
    private int minResponseSize;

    // in bytes
    private int maxResponseSize;

    private String subString;

    private boolean active = true;

    public Page(Integer id, URL url, int monitoringPeriod, long warningTime, long criticalTime,
                int responseCode, int minResponseSize, int maxResponseSize, String subString) {
        super(id);
        this.url = url;
        this.monitoringPeriod = monitoringPeriod;
        this.warningTime = warningTime;
        this.criticalTime = criticalTime;
        this.responseCode = responseCode;
        this.minResponseSize = minResponseSize;
        this.maxResponseSize = maxResponseSize;
        this.subString = subString;
    }

    public URL getUrl() {
        return url;
    }

    public int getMonitoringPeriod() {
        return monitoringPeriod;
    }

    public long getWarningTime() {
        return warningTime;
    }

    public long getCriticalTime() {
        return criticalTime;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getMinResponseSize() {
        return minResponseSize;
    }

    public int getMaxResponseSize() {
        return maxResponseSize;
    }

    public String getSubString() {
        return subString;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", monitoringPeriod=" + monitoringPeriod + " s" +
                ", warningTime=" + warningTime + " ms" +
                ", criticalTime=" + criticalTime + " ms" +
                ", responseCode=" + responseCode +
                ", minResponseSize=" + minResponseSize + " byte" +
                ", maxResponseSize=" + maxResponseSize + " byte" +
                ", subString='" + subString + '\'' +
                ", active=" + active +
                '}';
    }
}
