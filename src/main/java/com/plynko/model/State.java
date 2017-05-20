package com.plynko.model;

import java.time.LocalDateTime;

public class State {
    private Integer id;

    private Integer pageId;

    private LocalDateTime dateTime;

    private Status currentStatus = Status.UNKNOWN;

    private String information;
}
