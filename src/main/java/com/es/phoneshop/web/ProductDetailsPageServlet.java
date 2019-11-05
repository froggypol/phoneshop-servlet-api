package com.es.phoneshop.web;

import service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private Service service;

    @Override
    public void init() {
        service = service.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productDetailsId = request.getParameter("productId");
        request.setAttribute("products", service.getProduct(productDetailsId));
        request.getRequestDispatcher("/WEB-INF/pages/productDetailsPage.jsp").forward(request, response);
    }
}
