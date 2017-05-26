package com.plynko.model;

public class Config extends BaseEntity {

    // in seconds
    protected int monitoringPeriod;

    protected boolean active = true;

    protected Config(Integer id, int monitoringPeriod) {
        super(id);
        this.monitoringPeriod = monitoringPeriod;
    }

    public int getMonitoringPeriod() {
        return monitoringPeriod;
    }

    public boolean isActive() {
        return active;
    }
}
