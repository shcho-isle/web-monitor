package com.plynko.model;

import java.util.Date;

public class State extends BaseEntity {

    private Integer configId;

    private String name;

    private Status currentStatus;

    private Date dateTime;

    private String information;
    
    public State(Integer configId, String name) {
        this(null, configId, name, Status.UNKNOWN, "Unavailable");
    }

    public State(Integer id, Integer configId, String name, Status currentStatus, String information) {
        super(id);
        this.configId = configId;
        this.name = name;
        this.currentStatus = currentStatus;
        this.dateTime = new Date();
        this.information = information;
    }

    public Integer getConfigId() {
        return configId;
    }

    public String getName() {
        return name;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getInformation() {
        return information;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", configId=" + configId +
                ", name='" + name + '\'' +
                ", currentStatus=" + currentStatus +
                ", dateTime=" + dateTime +
                ", information='" + information + '\'' +
                '}';
    }
}
