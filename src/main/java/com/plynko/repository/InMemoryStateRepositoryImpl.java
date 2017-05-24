package com.plynko.repository;

import com.plynko.model.Page;
import com.plynko.model.State;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryStateRepositoryImpl implements StateRepository {

    private final Map<Integer, State> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

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
        if (repository.isEmpty()) {
            populate();
        }

        return repository.values();
    }

    private void populate() {
        PageRepository pageRepository = new InMemoryPageRepositoryImpl();
        List<Page> pages = new ArrayList<>(pageRepository.getAll());
        for (Page page: pages) {
            save(new State(page.getId(), page.getUrl()));
        }
    }
}
