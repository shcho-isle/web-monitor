package com.plynko.web;

import com.plynko.repository.InMemoryStateRepositoryImpl;
import com.plynko.repository.StateRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StateServlet extends HttpServlet {

    private StateRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryStateRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setAttribute("states", repository.getAll());
            request.getRequestDispatcher("/monitor.jsp").forward(request, response);
    }
}
