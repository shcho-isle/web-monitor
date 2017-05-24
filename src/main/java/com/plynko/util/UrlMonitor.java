package com.plynko.util;

import com.plynko.model.Page;

public class UrlMonitor implements Runnable {
    private Page page;

    public UrlMonitor(Page page) {
        this.page = page;
    }

    @Override
    public void run() {

    }
}
