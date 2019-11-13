package com.es.phoneshop.model.product;

import service.ProductService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DaoProductListListener implements ServletContextListener {

    private static CustomProductDao productListSingleton;

    private static ProductService productServiceSingleton;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        productListSingleton = CustomProductDao.getInstance();
        productServiceSingleton = ProductService.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
