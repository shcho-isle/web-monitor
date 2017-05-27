package com.plynko.repository;

import com.plynko.model.UrlConfig;

import java.util.Collection;

public interface ConfigRepository {
    UrlConfig save(UrlConfig urlConfig);

    // false if not found
    boolean delete(int id);

    // null if not found
    UrlConfig get(int id);

    Collection<UrlConfig> getAll();
}
