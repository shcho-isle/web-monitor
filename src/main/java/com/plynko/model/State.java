package com.plynko.model;

import java.time.LocalDateTime;

public class State extends BaseEntity {

    private Integer pageId;

    private String url;

    private Status currentStatus = Status.UNKNOWN;

    private LocalDateTime dateTime;

    private String information;
    
    public State(Integer pageId, String url) {
        this(pageId, Status.UNKNOWN, url, "Unavailable");
    }

    public State(Integer pageId, Status currentStatus, String url, String information) {
        this.pageId = pageId;
        this.url = url;
        this.currentStatus = currentStatus;
        this.dateTime = LocalDateTime.now();
        this.information = information;
    }

    public Integer getPageId() {
        return pageId;
    }

    public String getUrl() {
        return url;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getInformation() {
        return information;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", pageId=" + pageId +
                ", url='" + url + '\'' +
                ", currentStatus=" + currentStatus +
                ", dateTime=" + dateTime +
                ", information='" + information + '\'' +
                '}';
    }
}
