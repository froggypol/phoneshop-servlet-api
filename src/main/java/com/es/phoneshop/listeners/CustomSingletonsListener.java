package com.es.phoneshop.listeners;

import com.es.phoneshop.model.product.CustomProductDao;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.order.OrderService;
import recentlyviewed.RecentlyViewedProductsService;
import com.es.phoneshop.cart.SessionCartService;
import validation.DateValidator;
import validation.NameSurnameValidator;
import validation.QuantityValidator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CustomSingletonsListener implements ServletContextListener {

    private static CustomProductDao productListSingleton;

    private static ProductService productServiceSingleton;

    private static SessionCartService cartService;

    private static RecentlyViewedProductsService recentlyViewedProductsService;

    private static QuantityValidator quantityValidation;

    private static OrderService orderService;

    private static NameSurnameValidator nameSurnameValidator;

    private static DateValidator dateValidator;

//    private static DosService dosService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        productListSingleton = CustomProductDao.getInstance();
        productServiceSingleton = ProductService.getInstance();
        cartService = SessionCartService.getInstance();
        recentlyViewedProductsService = RecentlyViewedProductsService.getInstance();
        quantityValidation = QuantityValidator.getInstance();
        orderService = OrderService.getInstance();
        nameSurnameValidator = NameSurnameValidator.getInstance();
        dateValidator = DateValidator.getInstance();
//        DosService.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
