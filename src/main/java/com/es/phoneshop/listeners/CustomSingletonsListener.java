package com.es.phoneshop.listeners;

import com.es.phoneshop.model.product.CustomProductDao;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.order.CustomOrderDao;
import com.es.phoneshop.order.OrderService;
import com.es.phoneshop.web.DosService;
import recentlyviewed.RecentlyViewedProductsService;
import com.es.phoneshop.cart.SessionCartService;
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

    private static DosService dosService;

    private static CustomOrderDao customOrderDao;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        CustomProductDao.getInstance();
        ProductService.getInstance();
        SessionCartService.getInstance();
        RecentlyViewedProductsService.getInstance();
        QuantityValidator.getInstance();
        OrderService.getInstance();
        NameSurnameValidator.getInstance();
        DosService.getInstance();
        CustomOrderDao.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
