package com.es.phoneshop.listeners;

import com.es.phoneshop.model.product.CustomProductDao;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.order.OrderService;
import com.es.phoneshop.web.filter.DosService;
import recentlyviewed.RecentlyViewedProductsService;
import com.es.phoneshop.cart.SessionCartService;
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

    private static DosService dosService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        CustomProductDao.getInstance();
        ProductService.getInstance();
        SessionCartService.getInstance();
        RecentlyViewedProductsService.getInstance();
        QuantityValidator.getInstance();
        OrderService.getInstance();
        DosService.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
