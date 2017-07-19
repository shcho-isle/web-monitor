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
        String actual = getStateString(TEST_CONFIG1);
        Assert.assertTrue(actual.contains("status=WARNING") && actual.contains("response time:"));
    }

    @Test
    public void testCriticalTime() throws Exception {
        String actual = getStateString(TEST_CONFIG3);
        Assert.assertTrue(actual.contains("status=CRITICAL") && actual.contains("response time:"));
    }

    @Test
    public void testResponseCode() throws Exception {
        String actual = getStateString(TEST_CONFIG4);
        Assert.assertTrue(actual.contains("status=CRITICAL") && actual.contains("response code:"));
    }

    @Test
    public void testMinResponseSize() throws Exception {
        String actual = getStateString(TEST_CONFIG5);
        Assert.assertTrue(actual.contains("status=CRITICAL") && actual.contains("response size:"));
    }

    @Test
    public void testMaxResponseSize() throws Exception {
        String actual = getStateString(TEST_CONFIG6);
        Assert.assertTrue(actual.contains("status=CRITICAL") && actual.contains("response size:"));
    }

    @Test
    public void testSubString() throws Exception {
        String actual = getStateString(TEST_CONFIG7);
        Assert.assertTrue(actual.contains("status=CRITICAL") && actual.contains("substring is"));
    }

    @Test
    public void testOk() throws Exception {
        String actual = getStateString(TEST_CONFIG8);
        Assert.assertTrue(actual.contains("status=OK"));
    }

    @Test
    public void testPending() throws Exception {
        String actual = getStateString(TEST_CONFIG9);
        Assert.assertTrue(actual.contains("status=PENDING") && actual.contains("was excluded from monitoring"));
    }

    @Test
    public void testUnknown() throws Exception {
        String actual = getStateString(TEST_CONFIG10);
        Assert.assertTrue(actual.contains("status=UNKNOWN") && actual.contains("monitoring failed"));
    }
}
