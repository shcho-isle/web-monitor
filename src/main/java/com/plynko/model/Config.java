package com.plynko.model;

public class Config extends BaseEntity {

    // in seconds
    protected int monitoringPeriod;

    protected boolean active = true;

    protected boolean misconfigured = false;

    protected Config() {
        this.active = true;
        this.misconfigured = false;
    }

    public int getMonitoringPeriod() {
        return monitoringPeriod;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isMisconfigured() {
        return misconfigured;
    }

    public void setMonitoringPeriod(int monitoringPeriod) {
        this.monitoringPeriod = monitoringPeriod;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setMisconfigured(boolean misconfigured) {
        this.misconfigured = misconfigured;
    }
}
