package com.plynko.repository;

import com.plynko.model.UrlConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.plynko.ConfigTestData.*;

public class InMemoryConfigRepositoryTest {

    private ConfigRepository repository = InMemoryConfigRepositoryImpl.getInstance();

    @Before
    public void setUp() throws Exception {
        repository.save(TEST_CONFIG);
    }

    @Test
    public void testSave() throws Exception {
        UrlConfig newConfig = new UrlConfig(null, "http://www.i.ua/", 77, true, false, 222, 333, 200, 1111, 99999, "");
        UrlConfig created = repository.save(newConfig);
        newConfig.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(TEST_CONFIG, newConfig), repository.getAll());
    }
}
