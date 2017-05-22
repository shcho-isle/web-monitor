package com.plynko.model;

import java.time.LocalDateTime;

public class State extends BaseEntity {

    private Integer pageId;

    private String url;

    private LocalDateTime dateTime;

    private Status currentStatus = Status.UNKNOWN;

    private String information;

    public State(Integer pageId, String url, Status currentStatus, String information) {
        this.pageId = pageId;
        this.url = url;
        this.dateTime = LocalDateTime.now();
        this.currentStatus = currentStatus;
        this.information = information;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", pageId=" + pageId +
                ", url='" + url + '\'' +
                ", dateTime=" + dateTime +
                ", currentStatus=" + currentStatus +
                ", information='" + information + '\'' +
                '}';
    }
}
