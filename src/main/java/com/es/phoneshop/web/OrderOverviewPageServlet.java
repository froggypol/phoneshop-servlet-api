package com.es.phoneshop.web;

import com.es.phoneshop.custom.exceptions.CustomNoSuchOrderException;
import com.es.phoneshop.order.Order;
import com.es.phoneshop.order.OrderService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class OrderOverviewPageServlet extends HttpServlet {

    OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = OrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("order", returnOrder(request));
            request.getRequestDispatcher("/WEB-INF/pages/overview.jsp").forward(request, response);
        } catch (CustomNoSuchOrderException e) {
            request.getRequestDispatcher("/WEB-INF/pages/orderException.jsp").forward(request, response);
        }
    }

    private Order returnOrder(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String orderId = request.getRequestURI().substring(uri.lastIndexOf("/") + 1);
        Optional<Order> order = orderService.getOrderById(orderId);
       if (order.isPresent()) {
           return order.get();
        } else {
           throw new CustomNoSuchOrderException();
       }
    }
}
