package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.order.Order;
import com.es.phoneshop.order.OrderService;
import javafx.util.Pair;
import validation.ErrorMap;
import validation.NameSurnameValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class CheckoutPageServlet extends HttpServlet {

    private OrderService orderService;

    private ProductService productService;

    private NameSurnameValidator nameSurnameValidator;

    @Override
    public void init() {
        productService = ProductService.getInstance();
        orderService = OrderService.getInstance();
        nameSurnameValidator = NameSurnameValidator.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        Order order = new Order(cart);
        ErrorMap errorMap = new ErrorMap();
        orderService.checkoutStock(errorMap, cart);
        if (errorMap.getExceptionList().size() != 0) {
            request.setAttribute("errorMap", errorMap.getErrorMap());
            request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
            return;
        }
        if (order.getListCartItem().size() == 0) {
            request.setAttribute("products", productService.findProducts());
            request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
            return;
        }
        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        ErrorMap errorMap = validation(request);
        Order order = new Order(cart);
        try {
            String wayOfPayment = request.getParameter("payment");
            orderService.placeOrder(order, wayOfPayment);
            orderService.saveCustomerInfo(request, order);
        } catch (ParseException e) {
            errorMap.customException("date", "Invalid date format");
            request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
            return;
        }
        if (errorMap.getExceptionList().size() != 0) {
            request.setAttribute("errorMap", errorMap.getErrorMap());
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
            return;
        }
        productService.updateProductAfterOrder(request);
        response.sendRedirect(request.getContextPath() + "/checkout/overview/" + order.getId());
        cart.resetOrder();
    }

    private ErrorMap validation(HttpServletRequest request) {
        ErrorMap errorMap = new ErrorMap();
        nameSurnameValidator.validate(new Pair<>("name", request.getParameter("firstName")), errorMap);
        nameSurnameValidator.validate(new Pair<>("surname", request.getParameter("secondName")), errorMap);
        return errorMap;
    }
}
