package com.plynko.model;

public class Config extends BaseEntity {

    // in seconds
    protected int monitoringPeriod;

    protected boolean active = true;

    protected boolean misconfigured = false;

    protected Config(Integer id, int monitoringPeriod, boolean active, boolean misconfigured) {
        super(id);
        this.monitoringPeriod = monitoringPeriod;
        this.active = active;
        this.misconfigured = misconfigured;
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
}
