package com.plynko.model;

public class UrlConfig extends Config {

    private String url;

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

    public UrlConfig(Integer id, String url, int monitoringPeriod, boolean active, boolean misconfigured, long warningTime,
                     long criticalTime, int responseCode, int minResponseSize, int maxResponseSize, String subString) {
        super(id, monitoringPeriod, active, misconfigured);
        this.url = url;
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

    @Override
    public String toString() {
        return "UrlConfig{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", monitoringPeriod=" + monitoringPeriod + " s" +
                ", active=" + active +
                ", misconfigured=" + misconfigured +
                ", warningTime=" + warningTime + " ms" +
                ", criticalTime=" + criticalTime + " ms" +
                ", responseCode=" + responseCode +
                ", minResponseSize=" + minResponseSize + " byte" +
                ", maxResponseSize=" + maxResponseSize + " byte" +
                ", subString='" + subString + '\'' +
                '}';
    }
}
