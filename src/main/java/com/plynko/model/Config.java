package com.plynko.model;

public class Config extends BaseEntity {

    // in seconds
    protected int monitoringPeriod;

    protected boolean active = true;

    protected boolean misconfigured = false;

    protected Config() {
    }

    protected Config(Integer id, int monitoringPeriod) {
        super(id);
        this.active = true;
        this.misconfigured = false;
        this.monitoringPeriod = monitoringPeriod;
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
