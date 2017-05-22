package com.plynko.repository;

import com.plynko.model.Page;

import java.util.Collection;

public interface PageRepository {
    Page save(Page page);

    // false if not found
    boolean delete(int id);

    // null if not found
    Page get(int id);

    Collection<Page> getAll();
}
