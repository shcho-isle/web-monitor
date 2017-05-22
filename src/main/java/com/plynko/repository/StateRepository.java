package com.plynko.repository;

import com.plynko.model.State;

import java.util.Collection;

public interface StateRepository {
    State save(State state);

    // false if not found
    boolean delete(int pageId);

    // null if not found
    State get(int userId);

    Collection<State> getAll();
}
