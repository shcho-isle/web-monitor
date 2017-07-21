package com.plynko.repository;

import com.plynko.model.UrlConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static com.plynko.ConfigTestData.*;

public class InMemoryConfigRepositoryTest {

    private ConfigRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new InMemoryConfigRepositoryImpl();
        populateWithTestData(repository);
    }

    @Test
    public void testSave() throws Exception {
        UrlConfig newConfig = getCreated();
        UrlConfig created = repository.save(newConfig);
        newConfig.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(TEST_CONFIG1, TEST_CONFIG2, newConfig), repository.getAll());
    }

    @Test
    public void testGet() throws Exception {
        UrlConfig config = repository.get(1);
        MATCHER.assertEquals(TEST_CONFIG1, config);
    }

    @Test
    public void testUpdate() throws Exception {
        UrlConfig updated = getUpdated();
        repository.save(updated);
        MATCHER.assertEquals(updated, repository.get(CONFIG_ID1));
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<UrlConfig> all = repository.getAll();
        MATCHER.assertCollectionEquals(Arrays.asList(TEST_CONFIG1, TEST_CONFIG2), all);
    }

    @Test
    public void testGetAllInit() throws Exception {
        ConfigRepository emptyRepository = new InMemoryConfigRepositoryImpl();
        Collection<UrlConfig> all = emptyRepository.getAll();
        MATCHER.assertCollectionEquals(Arrays.asList(TEST_CONFIG1, TEST_CONFIG2), all);
    }
}