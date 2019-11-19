package com.es.phoneshop.web;

import com.es.phoneshop.cart.RecentlyViewedProductsService;
import com.es.phoneshop.custom.exceptions.CustomNoSuchElementException;
import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.parsing.ParseQuantityService;
import service.CartServiceSession;
import service.ProductService;
import service.SessionService;
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

    private CartServiceSession cartService;

    private HttpSession session;

    private CustomValidation customValidation;

    private RecentlyViewedProductsService recentlyViewedProducts;

    private SessionService sessionService;

    private ErrorMap errorMap;

    @Override
    public void init() {
        productService = ProductService.getInstance();
        cartService = CartServiceSession.getInstance();
        customValidation = CustomValidation.getInstance();
        recentlyViewedProducts = new RecentlyViewedProductsService();
        sessionService = SessionService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionService.setCart(cartService.getCart(request, response), request, response);
        try {
            showPage(request, response);
        } catch (CustomNoSuchElementException e) {
            request.getRequestDispatcher("/WEB-INF/pages/customError.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session = sessionService.getSession(request, response);
        String productQuantityToAdd = request.getParameter("quantity");
        String productDetailsId = getProductId(request);
        int quantityToAdd;
        errorMap = sessionService.getErrorMapFromSession(request, response);
        customValidation.validQuantity(errorMap, Locale.ENGLISH, request, response);
        Integer countExceptions = errorMap.getExceptionListSize("quantity");
        if (countExceptions == null) {
            quantityToAdd = new ParseQuantityService().parsingQuantity(Locale.ENGLISH, request, response);
            request.setAttribute("quantity", productQuantityToAdd);
            try {
                cartService.addToCart(productDetailsId, quantityToAdd, request, response);
            }
            catch (OutOfStockException e) {
                errorMap.customException("quantity", "OutOfStockException");
                sessionService.setErrorMapInSessionAttribute(request, response);
                response.sendRedirect(request.getRequestURI() + "?errorMessage=failed");
                return;
            }
        } else {
            request.setAttribute("error", errorMap.getExceptionList("quantity"));
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
        recentlyViewedProducts.getRecentlyViewedProducts(request, response);
        recentlyViewedProducts.addProductToViewedList(productService.getProductById(productId), request, response);
        recentlyViewedProducts.setRecentlyViewedProducts(request, response);
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

    public void setCartService(CartServiceSession cartService) {
        this.cartService = cartService;
    }
}
