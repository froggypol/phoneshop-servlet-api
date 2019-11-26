package com.es.phoneshop.web;

import com.es.phoneshop.utils.UtilParse;
import service.RecentlyViewedProductsService;
import com.es.phoneshop.custom.exceptions.CustomNoSuchElementException;
import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import service.SessionCartService;
import service.ProductService;
import validation.CustomValidation;
import validation.ErrorMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductService productService;

    private SessionCartService cartService;

    private CustomValidation customValidation;

    private RecentlyViewedProductsService recentlyViewedProductService;

    @Override
    public void init() {
        productService = ProductService.getInstance();
        cartService = SessionCartService.getInstance();
        customValidation = CustomValidation.getInstance();
        recentlyViewedProductService = recentlyViewedProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            showPage(request, response);
        } catch (CustomNoSuchElementException e) {
            request.getRequestDispatcher("/WEB-INF/pages/customError.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productQuantityToAdd = request.getParameter("quantity");
        String productDetailsId = getProductId(request);
        Product product = productService.getProductById(productDetailsId);
        int quantityToAdd;
        ErrorMap errorMap = new ErrorMap();
        customValidation.validQuantity(errorMap, request, response);
        try {
            quantityToAdd = UtilParse.parseIntByLocale(request.getLocale(), productQuantityToAdd);
        } catch (ParseException e) {
            quantityToAdd = 0;
        }
        if (errorMap.getExceptionList().size() == 0) {
            request.setAttribute("quantity", productQuantityToAdd);
            try {
                cartService.addToCart(productDetailsId, quantityToAdd, request, response);
            } catch (OutOfStockException e) {
                errorMap.customException(product, "Not enough products in stock");
                request.setAttribute("errorMap", errorMap);
                showPage(request, response);
                return;
            }
            response.sendRedirect(request.getRequestURI() + "?message=success");
        } else {
            errorMap.customException(product, "Incorrect input");
            request.setAttribute("errorMap", errorMap.getExceptionList());
            showPage(request, response);
        }
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = getProductId(request);
        Product product = productService.getProductById(productId);
        request.setAttribute("prod", product);
        request.setAttribute("quantity", request.getParameter("quantity"));
        request.getRequestDispatcher("/WEB-INF/pages/productDetailsPage.jsp").forward(request, response);
        recentlyViewedProductService.addProductToViewedList(productService.getProductById(productId), request);
    }

    public String getProductId(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String productId = request.getRequestURI().substring(uri.lastIndexOf("/") + 1);
        return productId;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCartService(SessionCartService cartService) {
        this.cartService = cartService;
    }
}
