package com.plynko.repository;

import com.plynko.model.UrlConfig;

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

public class InMemoryConfigRepositoryImpl implements ConfigRepository {

    private static final InMemoryConfigRepositoryImpl instance = new InMemoryConfigRepositoryImpl();

    private final Map<Integer, UrlConfig> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    private InMemoryConfigRepositoryImpl(){}

    public static InMemoryConfigRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public UrlConfig save(UrlConfig urlConfig) {
        if (urlConfig.isNew()) {
            urlConfig.setId(counter.incrementAndGet());
        }
        repository.put(urlConfig.getId(), urlConfig);
        return urlConfig;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public UrlConfig get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<UrlConfig> getAllCurrent() {
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

            UrlConfig urlConfig = new UrlConfig(null, url, monitoringPeriod, warningTime, criticalTime, responseCode, minResponseSize, maxResponseSize, subString);
            save(urlConfig);
        } catch (MalformedURLException | NumberFormatException e) {
            return;
        }
    }
}