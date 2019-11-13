package com.es.phoneshop.web;

import com.es.phoneshop.custom.exceptions.CustomNoSuchElementException;
import com.es.phoneshop.model.product.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductService service;

    @Override
    public void init() {
        service = ProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productDetailsId = request.getParameter("productId");
        try {
            Product product = service.getProductById(productDetailsId);
            request.setAttribute("products", product);
            request.getRequestDispatcher("/WEB-INF/pages/productDetailsPage.jsp").forward(request, response);
        } catch (CustomNoSuchElementException e) {
            request.getRequestDispatcher("/WEB-INF/pages/customError.jsp").forward(request, response);
        }
    }

    public void setService(ProductService service) {
        this.service = service;
    }
}
