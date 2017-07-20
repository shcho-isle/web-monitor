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

    public UrlConfig(Integer id, String url, int monitoringPeriod, long warningTime, long criticalTime, int responseCode,
                     int minResponseSize, int maxResponseSize, String subString) {
        super(id, monitoringPeriod);
        this.url = url;
        this.warningTime = warningTime;
        this.criticalTime = criticalTime;
        this.responseCode = responseCode;
        this.minResponseSize = minResponseSize;
        this.maxResponseSize = maxResponseSize;
        this.subString = subString;
    }

    public UrlConfig(String url) {
        this.url = url;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWarningTime(long warningTime) {
        this.warningTime = warningTime;
    }

    public void setCriticalTime(long criticalTime) {
        this.criticalTime = criticalTime;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setMinResponseSize(int minResponseSize) {
        this.minResponseSize = minResponseSize;
    }

    public void setMaxResponseSize(int maxResponseSize) {
        this.maxResponseSize = maxResponseSize;
    }

    public void setSubString(String subString) {
        this.subString = subString;
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
