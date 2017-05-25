package com.plynko.model;

import java.net.URL;
import java.util.Date;

public class State extends BaseEntity {

    private Integer pageId;

    private URL url;

    private Status currentStatus = Status.UNKNOWN;

    private Date dateTime;

    private String information;
    
    public State(Integer pageId, URL url) {
        this(pageId, url, Status.UNKNOWN, "Unavailable");
    }

    public State(Integer pageId, URL url, Status currentStatus, String information) {
        this.pageId = pageId;
        this.url = url;
        this.currentStatus = currentStatus;
        this.dateTime = new Date();
        this.information = information;
    }

    public Integer getPageId() {
        return pageId;
    }

    public URL getUrl() {
        return url;
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
                ", pageId=" + pageId +
                ", url='" + url + '\'' +
                ", currentStatus=" + currentStatus +
                ", dateTime=" + dateTime +
                ", information='" + information + '\'' +
                '}';
    }
}
