package com.plynko.repository;

import com.plynko.model.State;

import java.util.Collection;

public interface StateRepository {
    State save(State state);

    Collection<State> getAllCurrent();
}
