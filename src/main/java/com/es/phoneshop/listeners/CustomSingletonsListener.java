package com.es.phoneshop.listeners;

import com.es.phoneshop.model.product.CustomProductDao;
import service.ProductService;
import service.RecentlyViewedProductsService;
import service.SessionCartService;
import validation.QuantityValidator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CustomSingletonsListener implements ServletContextListener {

    private static CustomProductDao productListSingleton;

    private static ProductService productServiceSingleton;

    private static SessionCartService cartService;

    private static RecentlyViewedProductsService recentlyViewedProductsService;

    private static QuantityValidator quantityValidation;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        CustomProductDao.getInstance();
        ProductService.getInstance();
        SessionCartService.getInstance();
        RecentlyViewedProductsService.getInstance();
        QuantityValidator.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
