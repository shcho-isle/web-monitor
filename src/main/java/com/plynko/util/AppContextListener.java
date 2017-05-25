package com.plynko.util;

import com.plynko.model.Page;
import com.plynko.repository.InMemoryPageRepositoryImpl;
import com.plynko.repository.PageRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class AppContextListener implements ServletContextListener {

    private PageRepository repository = InMemoryPageRepositoryImpl.getInstance();

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        Collection<Page> pages = repository.getAllCurrent();

        for (Page page: pages) {
            scheduler.scheduleWithFixedDelay(new PageMonitor(page.getId()), 0, page.getMonitoringPeriod(), TimeUnit.SECONDS);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        scheduler.shutdown();
    }
}