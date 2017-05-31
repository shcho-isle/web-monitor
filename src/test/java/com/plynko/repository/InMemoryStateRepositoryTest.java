package com.plynko.repository;

import com.plynko.model.State;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static com.plynko.ConfigTestData.CONFIG_ID1;
import static com.plynko.StateTestData.*;

public class InMemoryStateRepositoryTest {
    private StateRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new InMemoryStateRepositoryImpl();
        populateWithTestData(repository);
    }

    @Test
    public void testSave() throws Exception {
        State newConfig = getCreated();
        State created = repository.save(newConfig);
        newConfig.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(TEST_STATE1, TEST_STATE2, newConfig), repository.getAllCurrent());
    }

    @Test
    public void testUpdate() throws Exception {
        State updated = getUpdated();
        repository.save(updated);
        MATCHER.assertCollectionEquals(Arrays.asList(updated, TEST_STATE2), repository.getAllCurrent());
    }

    @Test
    public void testGetAllCurrent() throws Exception {
        Collection<State> all = repository.getAllCurrent();
        MATCHER.assertCollectionEquals(Arrays.asList(TEST_STATE1, TEST_STATE2), all);
    }
}
