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
            new Page(null, "http://www.starwars.com/", 90, 4000, 7000, 200, 20000, 110000, "Children’s Online Privacy Policy"),
            new Page(null, "http://fregat.com/", 120, 2000, 4000, 200, 4000, 15000, "Способы оплаты"),
            new Page(null, "http://lurkmore.to/", 180, 3000, 4000, 200, 30000, 150000, "Добро пожаловать"),
            new Page(null, "http://npubop.livejournal.com/", 300, 6000, 12000, 200, 40000, 250000, "толик с прибором")
    );

    public static final List<State> STATES = Arrays.asList(
            new State(null, Status.OK, "http://stackoverflow.com/",  ""),
            new State(null, Status.WARNING, "http://www.starwars.com/", "Response time: 5555 ms"),
            new State(null, Status.CRITICAL, "http://fregat.com/",  ""),
            new State(null, Status.PENDING, "http://lurkmore.to/",  "Host is not scheduled to be checked..."),
            new State(null, Status.UNKNOWN, "http://npubop.livejournal.com/",  "No data received from the host yet")
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
