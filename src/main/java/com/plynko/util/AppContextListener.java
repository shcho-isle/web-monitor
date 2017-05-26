package com.plynko.util;

import com.plynko.model.Config;
import com.plynko.model.UrlConfig;
import com.plynko.repository.InMemoryConfigRepositoryImpl;
import com.plynko.repository.ConfigRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class AppContextListener implements ServletContextListener {

    private ConfigRepository repository = InMemoryConfigRepositoryImpl.getInstance();

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        Collection<UrlConfig> configs = repository.getAllCurrent();

        for (Config config : configs) {
            scheduler.scheduleWithFixedDelay(new UrlMonitoringTask(config.getId()), 0, config.getMonitoringPeriod(), TimeUnit.SECONDS);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        scheduler.shutdown();
    }
}