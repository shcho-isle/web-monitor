package com.plynko.model;

import java.util.Date;

public class State extends BaseEntity {

    private Integer configId;

    private String name;

    private Status status;

    private Date dateTime;

    private String information;

    public State(Integer id, Integer configId, String name, Status status, String information) {
        super(id);
        this.configId = configId;
        this.name = name;
        this.status = status;
        this.dateTime = new Date();
        this.information = information;
    }

    public Integer getConfigId() {
        return configId;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
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
                ", status=" + status +
                ", dateTime=" + dateTime +
                ", information='" + information + '\'' +
                '}';
    }
}
