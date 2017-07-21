package com.plynko.repository;

import com.plynko.model.State;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

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
        State newState = getCreated();
        State created = repository.save(newState);
        newState.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(TEST_STATE1, TEST_STATE2, newState), repository.getAllCurrent());
    }

    @Test
    public void testUpdate() throws Exception {
        State updatedState = getUpdated();
        repository.save(updatedState);
        MATCHER.assertCollectionEquals(Arrays.asList(updatedState, TEST_STATE2), repository.getAllCurrent());
    }

    @Test
    public void testGetAllCurrent() throws Exception {
        Collection<State> allStates = repository.getAllCurrent();
        MATCHER.assertCollectionEquals(Arrays.asList(TEST_STATE1, TEST_STATE2), allStates);
    }
}
