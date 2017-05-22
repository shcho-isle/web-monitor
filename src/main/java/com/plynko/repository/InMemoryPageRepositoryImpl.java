package com.plynko.repository;

import com.plynko.model.Page;
import com.plynko.util.Utils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryPageRepositoryImpl implements PageRepository {

    private final Map<Integer, Page> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        Utils.PAGES.forEach(this::save);
    }

    @Override
    public Page save(Page page) {
        if (page.isNew()) {
            page.setId(counter.incrementAndGet());
        }
        repository.put(page.getId(), page);
        return page;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Page get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Page> getAll() {
        return repository.values();
    }
}
