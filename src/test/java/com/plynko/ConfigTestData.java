package com.plynko;

import com.plynko.matcher.ModelMatcher;
import com.plynko.model.UrlConfig;

import java.util.Objects;

public class ConfigTestData {

    public static final UrlConfig TEST_CONFIG = new UrlConfig(null, "https://hh.ua/", 1, true, false, 1, 1, 1, 1, 1, "password");

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
}
