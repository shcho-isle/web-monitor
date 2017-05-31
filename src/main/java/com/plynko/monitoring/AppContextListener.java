package com.plynko.monitoring;

import com.plynko.model.State;
import com.plynko.model.Status;
import com.plynko.model.UrlConfig;
import com.plynko.repository.InMemoryConfigRepositoryImpl;
import com.plynko.repository.ConfigRepository;
import com.plynko.repository.InMemoryStateRepositoryImpl;
import com.plynko.repository.StateRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class AppContextListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        ConfigRepository configRepository = new InMemoryConfigRepositoryImpl();
        StateRepository stateRepository = InMemoryStateRepositoryImpl.getInstance();

        scheduler = Executors.newSingleThreadScheduledExecutor();
        Collection<UrlConfig> configs = configRepository.getAll();

        for (UrlConfig config : configs) {
            if (config.isActive() && !config.isMisconfigured()) {
                scheduler.scheduleWithFixedDelay(new UrlMonitoringTask(config.getId(), configRepository), 0, config.getMonitoringPeriod(), TimeUnit.SECONDS);
            } else {
                if (!config.isActive()) {
                    stateRepository.save(new State(null, config.getId(), config.getUrl(), Status.PENDING, "monitoring task is not active"));
                } else {
                    stateRepository.save(new State(null, config.getId(), config.getUrl(), Status.MISCONFIGURED, "monitoring task is misconfigured"));
                }
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        scheduler.shutdown();
    }
}