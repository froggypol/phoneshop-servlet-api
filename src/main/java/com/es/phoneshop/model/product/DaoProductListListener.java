package com.es.phoneshop.model.product;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DaoProductListListener implements ServletContextListener {

    private static CustomProductDao singletonProductList;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        singletonProductList = CustomProductDao.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
