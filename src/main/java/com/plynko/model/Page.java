package com.plynko.model;

public class Page extends BaseEntity {

    private String url;

    // in seconds
    private int monitoringPeriod;

    // in milliseconds
    private int warningTime;

    // in milliseconds
    private int criticalTime;

    private int responseCode;

    // in bytes
    private int minResponseSize;

    // in bytes
    private int maxResponseSize;

    private String subString;

    private boolean active = true;

    public Page(Integer id, String url, int monitoringPeriod, int warningTime, int criticalTime,
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

    public String getUrl() {
        return url;
    }

    public int getMonitoringPeriod() {
        return monitoringPeriod;
    }

    public int getWarningTime() {
        return warningTime;
    }

    public int getCriticalTime() {
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
