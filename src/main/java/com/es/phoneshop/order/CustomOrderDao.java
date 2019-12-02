package com.es.phoneshop.order;

import java.util.ArrayList;
import java.util.List;

public class CustomOrderDao {

    private static CustomOrderDao orderDao;

    private List<Order> orderList;

    private CustomOrderDao() {
        orderList = new ArrayList<>();
    }

    public static CustomOrderDao getInstance() {
        if (orderDao == null) {
            orderDao = new CustomOrderDao();
        }
        return orderDao;
    }

    public void saveOrder(Order order) {
        if (!orderList.contains(order))
        orderList.add(order);
    }

    public List<Order> getOrderList() {
        return orderList;
    }
}
