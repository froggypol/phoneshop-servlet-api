package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {

    Service service;

    ArrayListProductDao productListDao;

    @Override
    public void init() {
        service = new Service();
        productListDao = service.getProductDaoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", getSampleProducts());
        String productName = "HTC";
        List<Product> res = new ArrayList<>();
        res.addAll(productListDao.searchFor(productName));
        if(!res.isEmpty()) {
            request.setAttribute("products", res);
            request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
        }
    }

    private List<Product> getSampleProducts() {
        return productListDao.findProducts();
    }
}
