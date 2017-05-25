package com.plynko.web;

import com.plynko.repository.InMemoryStateRepositoryImpl;
import com.plynko.repository.StateRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/monitor")
public class StateServlet extends HttpServlet {

    private StateRepository repository = InMemoryStateRepositoryImpl.getInstance();

    private static final int RELOAD_PERIOD = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setIntHeader("Refresh", RELOAD_PERIOD);
        request.setAttribute("states", repository.getAll());
        request.setAttribute("lastUpdated", new Date());
        request.setAttribute("period", RELOAD_PERIOD);
        request.getRequestDispatcher("/monitor.jsp").forward(request, response);
    }
}
