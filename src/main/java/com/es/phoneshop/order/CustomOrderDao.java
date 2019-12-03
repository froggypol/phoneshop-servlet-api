package com.es.phoneshop.order;

import com.es.phoneshop.synchronization.object.SynchronizedObject;

import java.math.BigDecimal;
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
        synchronized (SynchronizedObject.getSynchronizedObject(order.getId())) {
            if (!orderList.contains(order))
                orderList.add(order);
        }
    }

    public void recalculate(String way, Order order) {
        if (way != null) {
            order.setSubTotal(order.getSubTotal().add(getDeliveryCost(way, order)));
        }
    }

    public BigDecimal getDeliveryCost(String way, Order order) {
        if (way.equals("cash")) {
            order.setDeliveryCost(new BigDecimal(15));
        } else if (way.equals("creditCard")) {
            order.setDeliveryCost(new BigDecimal(5));
        }
        return order.getDeliveryCost();
    }

    public List<Order> getOrderList() {
        return orderList;
    }
}
