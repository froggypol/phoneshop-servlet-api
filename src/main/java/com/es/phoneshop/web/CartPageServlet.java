package com.es.phoneshop.web;

import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import service.ProductService;
import service.SessionCartService;
import validation.CustomValidation;
import validation.ErrorMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CartPageServlet extends HttpServlet {

    private SessionCartService cartService;

    private CustomValidation customValidation;

    private ProductService productService;

    @Override
    public void init() {
        cartService = SessionCartService.getInstance();
        customValidation = CustomValidation.getInstance();
        productService = ProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ErrorMap errorMap = new ErrorMap();
        List<String> quantityList = Arrays.stream(request.getParameterValues("quantity")).collect(Collectors.toList());
        customValidation.validQuantity(errorMap, request, response);
        if (errorMap.getExceptionList().size() != 0) {
            request.setAttribute("errorMap", errorMap.getExceptionList());
            request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
            return;
        }
        try {
            cartService.updateCart(quantityList, request);
        } catch (ParseException e) {
            return;
        } catch (OutOfStockException e) {
            Product product = productService.getProductById(request.getParameter("productId"));
            errorMap.customException(product, "Not enough product in stock");
            request.setAttribute("errorMap", errorMap.getExceptionList());
            request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?message=success");
    }
}
