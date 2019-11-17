package com.es.phoneshop.web;

import com.es.phoneshop.custom.exceptions.CustomNoSuchElementException;
import com.es.phoneshop.model.product.Product;
import service.CartServiceSession;
import service.ProductService;
import validation.CustomValidation;
import validation.ErrorMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductService productService;

    private CartServiceSession cartService;

    private ErrorMap errorMap;

    private HttpSession session;

    @Override
    public void init() {
        productService = ProductService.getInstance();
        cartService = CartServiceSession.getInstance();
        errorMap = ErrorMap.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session = request.getSession();
        session.setAttribute("cart", cartService.getCart(session));
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
        Product product = productService.getProductById(productDetailsId);
        int quantityToAdd = 0;
        CustomValidation customValidation = new CustomValidation();
        try {
            if (customValidation.validQuantityNumberFormat(request, response)) {
                quantityToAdd = getQuantity(Locale.ENGLISH, productQuantityToAdd);
                request.setAttribute("quantity", productQuantityToAdd);
            } else {
                showPageInNANCase(request, response);
            }
            if (customValidation.validQuantityInStock(product, request, response))
                cartService.addToCart(productDetailsId, quantityToAdd, session);
            else {
                response.sendRedirect(request.getRequestURI() + "?errorMessage=failed");
                return;
            }
        } catch (ParseException e) {
            errorMap.customException("quantity", new ParseException(e.getMessage(), e.getErrorOffset()));
        }

        response.sendRedirect(request.getRequestURI() + "?message=success");
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = getProductId(request);
        Product product = productService.getProductById(productId);
        request.setAttribute("prod", product);
        request.setAttribute("quantity", request.getParameter("quantity"));
        request.getRequestDispatcher("/WEB-INF/pages/productDetailsPage.jsp").forward(request, response);
        cartService.addProductToViewedList(productService.getProductById(productId));
        request.getSession().setAttribute("recentlyViewedProducts", cartService.getCart(request.getSession()).getRecentlyViewedProducts());
    }

    public String getProductId(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String productId = request.getRequestURI().substring(uri.lastIndexOf("/") + 1);
        return productId;
    }

    private int getQuantity(Locale locale, String quantityStr) throws ParseException {
        return NumberFormat.getInstance(locale).parse(quantityStr).intValue();
    }

    private void showPageInNANCase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        errorMap.customException("quantity", new NumberFormatException());
        request.setAttribute("error", errorMap.getExceptionList("quantity"));
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
