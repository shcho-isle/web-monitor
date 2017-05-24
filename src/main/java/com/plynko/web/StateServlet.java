package com.plynko.web;

import com.plynko.repository.InMemoryStateRepositoryImpl;
import com.plynko.repository.StateRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/monitor")
public class StateServlet extends HttpServlet {

    private StateRepository repository;

    private static final int RELOAD_PERIOD = 10;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryStateRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setIntHeader("Refresh", RELOAD_PERIOD);
        request.setAttribute("states", repository.getAll());
        request.getRequestDispatcher("/monitor.jsp").forward(request, response);
    }
}
