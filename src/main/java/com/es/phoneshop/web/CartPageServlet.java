package com.es.phoneshop.web;

import com.es.phoneshop.custom.exceptions.OutOfStockException;
import javafx.util.Pair;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.cart.SessionCartService;
import validation.ErrorMap;
import validation.QuantityValidator;

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

    private QuantityValidator quantityValidator;

    private ProductService productService;

    @Override
    public void init() {
        cartService = SessionCartService.getInstance();
        quantityValidator = QuantityValidator.getInstance();
        productService = ProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ErrorMap errorMap = new ErrorMap();
        if (cartService.getCart(request).getListCartItem().size() == 0) {
            errorMap.customException("cart", "Empty cart");
            request.setAttribute("products", productService.findProducts());
            request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
            return;
        }
        List<String> quantityList = validQuantities(errorMap, request);
        if (errorMap.getExceptionList().size() != 0) {
            showPage(errorMap, request, response);
            return;
        }
        try {
            cartService.updateCart(quantityList, errorMap, request);
        } catch (ParseException | OutOfStockException e) {
            showPage(errorMap, request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?message=success");
    }

    private List<String> validQuantities(ErrorMap errorMap, HttpServletRequest request) {
        List<String> productsId = new ArrayList<>(Arrays.asList(request.getParameterValues("productId")));
        List<String> quantityList = new ArrayList<>(Arrays.asList(request.getParameterValues("quantity")));
        for(String quantityItem: quantityList) {
            Pair pairValidQuantity = new Pair<>(quantityItem, productsId.get(quantityList.indexOf(quantityItem)));
            quantityValidator.validate(pairValidQuantity, errorMap);
        }
        return quantityList;
    }

    private void showPage(ErrorMap errorMap, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        request.setAttribute("errorMap", errorMap.getErrorMap());
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }
}
