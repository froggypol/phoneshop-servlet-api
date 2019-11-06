package com.es.phoneshop.web;

import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {

    private ProductService service;

    @Override
    public void init() {
        service = service.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productNameQuery = request.getParameter("query");
        String fieldToSort = request.getParameter("sortField");
        String orderToSort = request.getParameter("order");
        String productDetailsId = request.getParameter("productId");
        request.setAttribute("products", service.findProducts(productNameQuery, fieldToSort, orderToSort));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    public ProductService getService() {
        return service;
    }

    public void setService(ProductService service) {
        this.service = service;
    }


}
