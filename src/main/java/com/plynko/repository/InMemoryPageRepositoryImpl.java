package com.plynko.repository;

import com.plynko.model.Page;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
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

    private static final InMemoryPageRepositoryImpl instance = new InMemoryPageRepositoryImpl();

    private final Map<Integer, Page> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    private InMemoryPageRepositoryImpl(){}

    public static InMemoryPageRepositoryImpl getInstance() {
        return instance;
    }

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
    public Collection<Page> getAllCurrent() {
        if (repository.isEmpty()) {
            populateStates();
        }
        return repository.values();
    }

    private void populateStates() {
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
                validateAndSave(properties);
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("error reading folder %s: %s", propertiesPath, e.getMessage()), e);
        }
    }

    private void validateAndSave(Properties properties) {
        try {
            URL url = new URL(properties.getProperty("monitoring.url"));

            int monitoringPeriod = Integer.parseInt(properties.getProperty("monitoring.period"));
            if (monitoringPeriod <= 0) {
                return;
            }

            long warningTime = Integer.parseInt(properties.getProperty("response.time.warning"));
            int criticalTime = Integer.parseInt(properties.getProperty("response.time.critical"));
            int responseCode = Integer.parseInt(properties.getProperty("response.code"));
            int minResponseSize = Integer.parseInt(properties.getProperty("response.size.min"));
            int maxResponseSize = Integer.parseInt(properties.getProperty("response.size.max"));
            String subString = properties.getProperty("response.substring");

            Page page = new Page(null, url, monitoringPeriod, warningTime, criticalTime, responseCode, minResponseSize, maxResponseSize, subString);
            save(page);
        } catch (MalformedURLException | NumberFormatException e) {
            return;
        }
    }
}
