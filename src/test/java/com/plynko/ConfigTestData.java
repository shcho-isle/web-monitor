package com.plynko;

import com.plynko.matcher.ModelMatcher;
import com.plynko.model.UrlConfig;
import com.plynko.repository.ConfigRepository;

import java.util.Objects;

public class ConfigTestData {

    public static final int CONFIG_ID1 = 1;
    public static final int CONFIG_ID2 = CONFIG_ID1 + 1;

    public static final UrlConfig TEST_CONFIG1 = getTestConfig1();
    public static final UrlConfig TEST_CONFIG2 = getTestConfig2();

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
        UrlConfig testConfig1 = getTestConfig1();
        testConfig1.setId(null);
        repository.save(testConfig1);

        UrlConfig testConfig2 = getTestConfig2();
        testConfig2.setId(null);
        repository.save(testConfig2);
    }

    public static UrlConfig getCreated() {
        UrlConfig created = new UrlConfig("http://www.i.ua/");
        created.setMonitoringPeriod(3);
        created.setWarningTime(3);
        created.setCriticalTime(3);
        created.setResponseCode(3);
        created.setMinResponseSize(3);
        created.setMaxResponseSize(3);
        created.setSubString("");
        return created;
    }

    public static UrlConfig getUpdated() {
        UrlConfig updated = new UrlConfig("http://hh.ua/");
        updated.setId(CONFIG_ID1);
        updated.setMonitoringPeriod(4);
        updated.setWarningTime(4);
        updated.setCriticalTime(4);
        updated.setResponseCode(4);
        updated.setMinResponseSize(4);
        updated.setMaxResponseSize(4);
        updated.setSubString("updated substring");
        return updated;
    }

    public static UrlConfig getTestConfig1() {
        UrlConfig testConfig1 = new UrlConfig("http://www.starwars.com/");
        testConfig1.setId(CONFIG_ID1);
        testConfig1.setMonitoringPeriod(100);
        testConfig1.setWarningTime(10_000);
        testConfig1.setCriticalTime(20_000);
        testConfig1.setResponseCode(200);
        testConfig1.setMinResponseSize(1);
        testConfig1.setMaxResponseSize(10_000_000);
        return testConfig1;
    }

    public static UrlConfig getTestConfig2() {
        UrlConfig testConfig2 = new UrlConfig("https://igov.org.ua/");
        testConfig2.setId(CONFIG_ID2);
        testConfig2.setMonitoringPeriod(2);
        testConfig2.setWarningTime(2);
        testConfig2.setCriticalTime(2);
        testConfig2.setResponseCode(2);
        testConfig2.setMinResponseSize(2);
        testConfig2.setMaxResponseSize(2);
        testConfig2.setSubString("substring2");
        return testConfig2;
    }
}
