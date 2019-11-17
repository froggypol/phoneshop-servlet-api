package com.es.phoneshop.model.product;

import service.CartServiceSession;
import service.ProductService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DaoProductListListener implements ServletContextListener {

    private static CustomProductDao productListSingleton;

    private static ProductService productServiceSingleton;

    private static CartServiceSession cartServiceSingleton;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        productListSingleton = CustomProductDao.getInstance();
        productServiceSingleton = ProductService.getInstance();
        cartServiceSingleton = CartServiceSession.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
