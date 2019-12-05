package com.es.phoneshop.web;

import com.es.phoneshop.cart.SessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {

    SessionCartService cartService;

    @Override
    public void init() {
        cartService = SessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productID = request.getParameter("productId");
        cartService.deleteCartItem(productID, request);
        response.sendRedirect(request.getRequestURI());
    }
}
