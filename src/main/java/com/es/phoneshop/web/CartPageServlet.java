package com.es.phoneshop.web;

import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import service.ProductService;
import service.SessionCartService;
import validation.CartServiceQuantityValidator;
import validation.ErrorMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartPageServlet extends HttpServlet {

    private SessionCartService cartService;

    private CartServiceQuantityValidator cartServiceQuantityValidator;

    private ProductService productService;

    @Override
    public void init() {
        cartService = SessionCartService.getInstance();
        cartServiceQuantityValidator = CartServiceQuantityValidator.getInstance();
        productService = ProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ErrorMap errorMap = new ErrorMap();
        List<String> quantityList = validQuantities(errorMap, request);
        if (errorMap.getExceptionList().size() != 0) {
            showPage(errorMap, request, response);
            return;
        }
        try {
            cartService.updateCart(quantityList, request);
        } catch (ParseException e) {
            showPage(errorMap, request, response);
            return;
        } catch (OutOfStockException e) {
            Product product = productService.getProductById(request.getParameter("productId"));
            errorMap.customException("quantity&" + product.getId(), "Not enough product in stock");
            showPage(errorMap, request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?message=success");
    }

    private List<String> validQuantities(ErrorMap errorMap, HttpServletRequest request) {
        List<String> productsId = new ArrayList<>(Arrays.asList(request.getParameterValues("productId")));
        List<String> quantityList = new ArrayList<>(Arrays.asList(request.getParameterValues("quantity")));
        for(String quantityItem: quantityList) {
            cartServiceQuantityValidator.validate(quantityItem, productsId.get(quantityList.indexOf(quantityItem)), errorMap);
        }
        return quantityList;
    }

    private void showPage(ErrorMap errorMap, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        request.setAttribute("errorMap", errorMap.getErrorMap());
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }
}
