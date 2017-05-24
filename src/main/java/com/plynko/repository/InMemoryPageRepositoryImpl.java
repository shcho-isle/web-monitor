package com.plynko.repository;

import com.plynko.model.Page;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryPageRepositoryImpl implements PageRepository {

    private final Map<Integer, Page> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Page save(Page page) {
        if (page.isNew()) {
            page.setId(counter.incrementAndGet());
        }
        repository.put(page.getId(), page);
        return page;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Page get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Page> getAll() {
        if (repository.isEmpty()) {
            populate();
        }
        return repository.values();
    }

    private void populate() {
        Path propertiesPath = null;
        try {
            propertiesPath = Paths.get(getClass().getClassLoader().getResource("urls").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert propertiesPath != null;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(propertiesPath, "*.properties")) {
            for (Path entry : directoryStream) {
                Properties properties = new Properties();
                InputStream propertiesStream = Files.newInputStream(entry);
                properties.load(propertiesStream);
                System.out.println(properties.stringPropertyNames());
                Page page = new Page(null,
                        properties.getProperty("monitoring.url"),
                        Integer.parseInt(properties.getProperty("monitoring.period")),
                        Integer.parseInt(properties.getProperty("response.time.warning")),
                        Integer.parseInt(properties.getProperty("response.time.critical")),
                        Integer.parseInt(properties.getProperty("response.code")),
                        Integer.parseInt(properties.getProperty("response.size.min")),
                        Integer.parseInt(properties.getProperty("response.size.max")),
                        properties.getProperty("response.substring"));
                save(page);
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("error reading folder %s: %s", propertiesPath, e.getMessage()), e);
        }
    }
}
