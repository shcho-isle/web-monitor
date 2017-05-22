package com.plynko.repository;

import com.plynko.model.State;
import com.plynko.util.Utils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryStateRepositoryImpl implements StateRepository {

    private final Map<Integer, State> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        Utils.STATES.forEach(this::save);
    }

    @Override
    public State save(State state) {
        if (state.isNew()) {
            state.setId(counter.incrementAndGet());
        }
        repository.put(state.getId(), state);
        return state;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public State get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<State> getAll() {
        return repository.values();
    }
}
