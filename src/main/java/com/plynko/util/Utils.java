package com.plynko.util;

import com.plynko.model.Page;
import com.plynko.model.State;
import com.plynko.model.Status;
import com.plynko.repository.InMemoryPageRepositoryImpl;
import com.plynko.repository.InMemoryStateRepositoryImpl;
import com.plynko.repository.PageRepository;
import com.plynko.repository.StateRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static final List<Page> PAGES = Arrays.asList(
            new Page(null, "http://stackoverflow.com/", 60, 3000, 5000, 200, 20000, 50000, "Looking for more?"),
            new Page(null, "http://www.starwars.com/", 90, 4000, 7000, 200, 20000, 100000, "Children’s Online Privacy Policy")
    );

    public static final List<State> STATES = Arrays.asList(
            new State(null, "http://stackoverflow.com/", Status.OK, ""),
            new State(null, "http://www.starwars.com/", Status.WARNING, "Response time: 5555 ms")
    );

    public static void main(String[] args) {
        PageRepository pageRepository = new InMemoryPageRepositoryImpl();
        StateRepository stateRepository = new InMemoryStateRepositoryImpl();

        List<Page> pages = new ArrayList<>(pageRepository.getAll());
        List<State> states = new ArrayList<>(stateRepository.getAll());

        pages.forEach(System.out::println);
        states.forEach(System.out::println);
    }
}
