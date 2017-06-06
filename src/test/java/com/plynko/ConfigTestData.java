package com.plynko;

import com.plynko.matcher.ModelMatcher;
import com.plynko.model.UrlConfig;
import com.plynko.repository.ConfigRepository;

import java.util.Objects;

public class ConfigTestData {

    public static final int CONFIG_ID1 = 1;
    public static final int CONFIG_ID2 = CONFIG_ID1 + 1;

    public static final UrlConfig TEST_CONFIG1 = new UrlConfig(CONFIG_ID1, "http://www.starwars.com/", 100, true, false, 1, 10_000, 200, 1, 10_000_000, null);
    public static final UrlConfig TEST_CONFIG2 = new UrlConfig(CONFIG_ID2, "https://igov.org.ua/", 2, true, false, 2, 2, 2, 2, 2, "substring2");

    public static final ModelMatcher<UrlConfig> MATCHER = ModelMatcher.of(
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
        repository.save(new UrlConfig(null, "http://www.starwars.com/", 100, true, false, 1, 10_000, 200, 1, 10_000_000, null));
        repository.save(new UrlConfig(null, "https://igov.org.ua/", 2, true, false, 2, 2, 2, 2, 2, "substring2"));
    }

    public static UrlConfig getCreated() {
        return new UrlConfig(null, "http://www.i.ua/", 3, true, false, 3, 3, 3, 3, 3, "");
    }

    public static UrlConfig getUpdated() {
        return new UrlConfig(CONFIG_ID1, "http://hh.ua/", 4, true, false, 4, 4, 4, 4, 4, "updated substring");
    }
}
