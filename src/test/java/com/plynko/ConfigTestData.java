package com.plynko;

import com.plynko.matcher.ModelMatcher;
import com.plynko.model.UrlConfig;
import com.plynko.repository.ConfigRepository;

import java.util.Objects;

public class ConfigTestData {

    public static final UrlConfig TEST_CONFIG1 = new UrlConfig(1, "https://hh.ua/", 1, true, false, 1, 1, 1, 1, 1, "substring1");
    public static final UrlConfig TEST_CONFIG2 = new UrlConfig(2, "https://igov.org.ua/", 2, false, true, 2, 2, 2, 2, 2, "substring2");

    public static final ModelMatcher<UrlConfig> MATCHER = ModelMatcher.of(UrlConfig.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getMonitoringPeriod(), actual.getMonitoringPeriod())
                            && Objects.equals(expected.isActive(), actual.isActive())
                            && Objects.equals(expected.getUrl(), actual.getUrl())
                            && Objects.equals(expected.getWarningTime(), actual.getWarningTime())
                            && Objects.equals(expected.getCriticalTime(), actual.getCriticalTime())
                            && Objects.equals(expected.getResponseCode(), actual.getResponseCode())
                            && Objects.equals(expected.getMinResponseSize(), actual.getMinResponseSize())
                            && Objects.equals(expected.getMaxResponseSize(), actual.getMaxResponseSize())
                            && Objects.equals(expected.getSubString(), actual.getSubString())
                    )
    );

    public static void populateWithTestData(ConfigRepository repository) {
        repository.save(new UrlConfig(null, "https://hh.ua/", 1, true, false, 1, 1, 1, 1, 1, "substring1"));
        repository.save(new UrlConfig(null, "https://igov.org.ua/", 2, false, true, 2, 2, 2, 2, 2, "substring2"));
    }
}
