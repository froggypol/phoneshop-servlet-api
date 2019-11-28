package com.es.phoneshop.listeners;

import com.es.phoneshop.model.product.CustomProductDao;
import service.ProductService;
import service.RecentlyViewedProductsService;
import service.SessionCartService;
import validation.CartServiceQuantityValidator;
import validation.PDPQuantityValidation;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CustomSingletonsListener implements ServletContextListener {

    private static CustomProductDao productListSingleton;

    private static ProductService productServiceSingleton;

    private static SessionCartService cartService;

    private static RecentlyViewedProductsService recentlyViewedProductsService;

    private static PDPQuantityValidation pdpQuantityValidation;

    private static CartServiceQuantityValidator cartServiceQuantityValidator;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        productListSingleton = CustomProductDao.getInstance();
        productServiceSingleton = ProductService.getInstance();
        cartService = SessionCartService.getInstance();
        recentlyViewedProductsService = RecentlyViewedProductsService.getInstance();
        pdpQuantityValidation = PDPQuantityValidation.getInstance();
        cartServiceQuantityValidator = CartServiceQuantityValidator.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
