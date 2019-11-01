package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {

    private ArrayListProductDao productListDao;

    private Service service;

    @Override
    public void init() {
        service = service.getInstance();
        productListDao = productListDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", getSampleProducts());
//        String productName = request.getParameter("query");
//        List<Product> res = new ArrayList<>();
//        res.addAll(productListDao.searchFor(productName));
//        if(!res.isEmpty()) {
//            request.setAttribute("products", res);
            request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
//        }
    }

    private List<Product> getSampleProducts() {
        return service.findProducts();
    }

    public Service getService(){
        return service;
    }
}
