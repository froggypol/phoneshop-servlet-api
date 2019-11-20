package com.es.phoneshop.web;

import service.RecentlyViewedProductsService;
import com.es.phoneshop.custom.exceptions.CustomNoSuchElementException;
import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.parsing.ParseQuantityService;
import service.SessionCartService;
import service.ProductService;
import validation.CustomValidation;
import validation.ErrorMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductService productService;

    private SessionCartService cartService;

    private HttpSession session;

    private CustomValidation customValidation;

    private RecentlyViewedProductsService recentlyViewedProducts;

    private ErrorMap errorMap;

    @Override
    public void init() {
        productService = ProductService.getInstance();
        cartService = SessionCartService.getInstance();
        customValidation = CustomValidation.getInstance();
        recentlyViewedProducts = RecentlyViewedProductsService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cartService.setCart(cartService.getCart(request, response), request, response);
        try {
            showPage(request, response);
        } catch (CustomNoSuchElementException e) {
            request.getRequestDispatcher("/WEB-INF/pages/customError.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session = request.getSession();
        String productQuantityToAdd = request.getParameter("quantity");
        String productDetailsId = getProductId(request);
        int quantityToAdd;
        errorMap = new ErrorMap();
        customValidation.validQuantity(errorMap, Locale.ENGLISH, request, response);
        int countExceptions = errorMap.getExceptionList().size();
        if (countExceptions == 0) {
            quantityToAdd = new ParseQuantityService().parsingQuantity(Locale.ENGLISH, request, response);
            request.setAttribute("quantity", productQuantityToAdd);
            try {
                cartService.addToCart(productDetailsId, quantityToAdd, request, response);
            }
            catch (OutOfStockException e) {
                errorMap.customException("quantity", "OutOfStockException");
                response.sendRedirect(request.getRequestURI() + "?errorMessage=failed");
                return;
            }
        } else {
            request.setAttribute("error", errorMap.getExceptionList());
            showPageInInvalidQuantityCase(request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?message=success");
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = getProductId(request);
        Product product = productService.getProductById(productId);
        request.setAttribute("prod", product);
        request.setAttribute("quantity", request.getParameter("quantity"));
        request.getRequestDispatcher("/WEB-INF/pages/productDetailsPage.jsp").forward(request, response);
        recentlyViewedProducts.getRecentlyViewedProducts(request);
        recentlyViewedProducts.addProductToViewedList(productService.getProductById(productId));
        recentlyViewedProducts.setRecentlyViewedProducts(request);
    }

    public String getProductId(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String productId = request.getRequestURI().substring(uri.lastIndexOf("/") + 1);
        return productId;
    }

    private void showPageInInvalidQuantityCase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showPage(request, response);
        return;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCartService(SessionCartService cartService) {
        this.cartService = cartService;
    }
}
