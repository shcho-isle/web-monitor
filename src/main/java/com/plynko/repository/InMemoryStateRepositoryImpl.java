package com.plynko.repository;

import com.plynko.model.Config;
import com.plynko.model.UrlConfig;
import com.plynko.model.State;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryStateRepositoryImpl implements StateRepository {

    private static final InMemoryStateRepositoryImpl instance = new InMemoryStateRepositoryImpl();

    private final Map<Integer, State> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    private InMemoryStateRepositoryImpl(){}

    public static InMemoryStateRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public State save(State state) {
        if (state.isNew()) {
            state.setId(counter.incrementAndGet());
        }
        repository.put(state.getConfigId(), state);
        return state;
    }

    @Override
    public State get(int configId) {
        return repository.get(configId);
    }

    @Override
    public Collection<State> getAll() {
        if (repository.isEmpty()) {
            populate();
        }

        return repository.values();
    }

    private void populate() {
        ConfigRepository configRepository = InMemoryConfigRepositoryImpl.getInstance();
        Collection<UrlConfig> configs = new ArrayList<>(configRepository.getAllCurrent());
        for (UrlConfig config : configs) {
            save(new State(config.getId(), config.getUrl().toString()));
        }
    }
}
