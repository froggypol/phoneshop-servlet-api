package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {

    private ProductService service;

    @Override
    public void init() {
        service = ProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> listProduct = new ArrayList<>();
        String productNameQuery = request.getParameter("query");
        String fieldToSort = request.getParameter("sortField");
        String orderToSort = request.getParameter("order");
        if(productNameQuery != "" && fieldToSort != "" && orderToSort != "") {
            listProduct = getList(productNameQuery, fieldToSort, orderToSort);
            pageForward(listProduct, request, response);
        }
        else {
            redirectPage(listProduct, request, response);
        }
    }

    private void pageForward(List<Product> products, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    private void redirectPage(List<Product> listProduct, HttpServletRequest request, HttpServletResponse response) throws IOException{
        listProduct = service.getCustomProductDao().getProductList();
        request.setAttribute("products", listProduct);
        response.sendRedirect(request.getRequestURI());
    }

    public List<Product> getList(String productNameQuery, String fieldToSort, String orderToSort) {
        return service.findProducts(productNameQuery, fieldToSort, orderToSort);
    }

    public void setService(ProductService service) {
        this.service = service;
    }
}
