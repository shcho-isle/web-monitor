package com.plynko.repository;

import com.plynko.model.UrlConfig;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryConfigRepositoryImpl implements ConfigRepository {

    private final Map<Integer, UrlConfig> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public UrlConfig save(UrlConfig urlConfig) {
        if (urlConfig.isNew()) {
            urlConfig.setId(counter.incrementAndGet());
        }
        repository.put(urlConfig.getId(), urlConfig);
        return urlConfig;
    }

    @Override
    public UrlConfig get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<UrlConfig> getAll() {
        if (repository.isEmpty()) {
            populateConfigs();
        }
        return repository.values();
    }

    private void populateConfigs() {
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
                properties.load(Files.newBufferedReader(entry));
                validateAndSave(properties);
            }
        } catch (IOException e) {
            throw new FileSystemNotFoundException(String.format("error reading folder %s: %s", propertiesPath, e.getMessage()));
        }
    }

    private void validateAndSave(Properties properties) {
        String url = properties.getProperty("urlConfig.url");
        UrlConfig urlConfig = new UrlConfig(url);
        if (url == null) {
            urlConfig.setMisconfigured(true);
        }

        try {
            int monitoringPeriod = Integer.parseInt(properties.getProperty("config.monitoringPeriod"));
            if (monitoringPeriod <= 0) {
                throw new NumberFormatException("Monitoring period cannot be negative number.");
            }
            urlConfig.setMonitoringPeriod(monitoringPeriod);

            long warningTime = Integer.parseInt(properties.getProperty("urlConfig.warningTime"));
            urlConfig.setWarningTime(warningTime);

            int criticalTime = Integer.parseInt(properties.getProperty("urlConfig.criticalTime"));
            urlConfig.setCriticalTime(criticalTime);

            int responseCode = Integer.parseInt(properties.getProperty("urlConfig.responseCode"));
            urlConfig.setResponseCode(responseCode);

            int minResponseSize = Integer.parseInt(properties.getProperty("urlConfig.minResponseSize"));
            urlConfig.setMinResponseSize(minResponseSize);

            int maxResponseSize = Integer.parseInt(properties.getProperty("urlConfig.maxResponseSize"));
            urlConfig.setMaxResponseSize(maxResponseSize);
            
            String subString = properties.getProperty("urlConfig.subString");
            urlConfig.setSubString(subString);
        } catch (NumberFormatException e) {
            urlConfig.setMisconfigured(true);
        } finally {
            save(urlConfig);
        }
    }
}
