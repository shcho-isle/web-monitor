package com.plynko.monitoring;

import com.plynko.StateTestData;
import com.plynko.model.State;
import com.plynko.repository.ConfigRepository;
import com.plynko.repository.InMemoryConfigRepositoryImpl;
import com.plynko.repository.InMemoryStateRepositoryImpl;
import com.plynko.repository.StateRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static com.plynko.ConfigTestData.*;

public class UrlMonitoringTaskTest {

    private ConfigRepository configRepository;
    private StateRepository stateRepository;

    @Before
    public void setUp() throws Exception {
        configRepository = new InMemoryConfigRepositoryImpl();
        stateRepository = new InMemoryStateRepositoryImpl();
    }

    @Test
    public void testWarningTime() throws Exception {
        configRepository.save(TEST_CONFIG1);
        new UrlMonitoringTask(CONFIG_ID1, configRepository, stateRepository).run();
        Collection<State> all = stateRepository.getAllCurrent();
        com.plynko.StateTestData.MATCHER.assertCollectionEquals(Collections.singletonList(StateTestData.TEST_STATE1), all);
    }
}
