package com.es.phoneshop.web;

import com.google.gson.Gson;
import com.es.phoneshop.model.product.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AjaxSortingServlet extends HttpServlet {

    private ProductService service;

    @Override
    public void init() {
        service = ProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productNameQuery = request.getParameter("query");
        String fieldToSort = request.getParameter("sortField");
        String orderToSort = request.getParameter("order");

        Gson gson = new Gson();

        PrintWriter out = response.getWriter();

        out.print(gson.toJson(service.findProducts(productNameQuery, fieldToSort, orderToSort)));
        out.flush();
        out.close();
    }

    public void setService(ProductService service) {
        this.service = service;
    }
}
