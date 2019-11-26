package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {

    private ProductService service;

    @Override
    public void init() {
        service = ProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> listProduct;
        String productNameQuery = request.getParameter("query");
        String fieldToSort = request.getParameter("sortField");
        String orderToSort = request.getParameter("order");
        listProduct = getList(productNameQuery, fieldToSort, orderToSort);
        request.setAttribute("products", listProduct);
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    public List<Product> getList(String productNameQuery, String fieldToSort, String orderToSort) {
        return service.findProducts(productNameQuery, fieldToSort, orderToSort);
    }

    public void setService(ProductService service) {
        this.service = service;
    }
}
