package com.plynko.monitoring;

import com.plynko.model.State;
import com.plynko.model.UrlConfig;
import com.plynko.repository.ConfigRepository;
import com.plynko.repository.InMemoryConfigRepositoryImpl;
import com.plynko.repository.InMemoryStateRepositoryImpl;
import com.plynko.repository.StateRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static com.plynko.ConfigTestData.*;

public class UrlMonitoringTaskTest {

    private ConfigRepository configRepository;
    private StateRepository stateRepository;

    @Before
    public void setUp() throws Exception {
        configRepository = new InMemoryConfigRepositoryImpl();
        stateRepository = new InMemoryStateRepositoryImpl();
    }

    private String getStateString(UrlConfig config) {
        configRepository.save(config);
        new UrlMonitoringTask(CONFIG_ID1, configRepository, stateRepository).run();

        Collection<State> all = stateRepository.getAllCurrent();
        Assert.assertTrue(all.size() == 1);

        return all.toString();
    }

    @Test
    public void testWarningTime() throws Exception {
        UrlConfig config = getTestConfig();
        config.setWarningTime(1);
        String actual = getStateString(config);
        Assert.assertTrue(actual.contains("status=WARNING") && actual.contains("response time:"));
    }

    @Test
    public void testCriticalTime() throws Exception {
        UrlConfig config = getTestConfig();
        config.setCriticalTime(1);
        String actual = getStateString(config);
        Assert.assertTrue(actual.contains("status=CRITICAL") && actual.contains("response time:"));
    }

    @Test
    public void testResponseCode() throws Exception {
        UrlConfig config = getTestConfig();
        config.setResponseCode(400);
        String actual = getStateString(config);
        Assert.assertTrue(actual.contains("status=CRITICAL") && actual.contains("response code:"));
    }

    @Test
    public void testMinResponseSize() throws Exception {
        UrlConfig config = getTestConfig();
        config.setMinResponseSize(10_000_000);
        String actual = getStateString(config);
        Assert.assertTrue(actual.contains("status=CRITICAL") && actual.contains("response size:"));
    }

    @Test
    public void testMaxResponseSize() throws Exception {
        UrlConfig config = getTestConfig();
        config.setMaxResponseSize(2);
        String actual = getStateString(config);
        Assert.assertTrue(actual.contains("status=CRITICAL") && actual.contains("response size:"));
    }

    @Test
    public void testSubString() throws Exception {
        UrlConfig config = getTestConfig();
        config.setSubString("absent substring");
        String actual = getStateString(config);
        Assert.assertTrue(actual.contains("status=CRITICAL") && actual.contains("substring is"));
    }

    @Test
    public void testOk() throws Exception {
        UrlConfig config = getTestConfig();
        String actual = getStateString(config);
        Assert.assertTrue(actual.contains("status=OK"));
    }

    @Test
    public void testPending() throws Exception {
        UrlConfig config = getTestConfig();
        config.setActive(false);
        String actual = getStateString(config);
        Assert.assertTrue(actual.contains("status=PENDING") && actual.contains("was excluded from monitoring"));
    }

    @Test
    public void testUnknown() throws Exception {
        UrlConfig config = getTestConfig();
        config.setUrl("ftps://www.starwars.com/");
        String actual = getStateString(config);
        Assert.assertTrue(actual.contains("status=UNKNOWN") && actual.contains("monitoring failed"));
    }
}
