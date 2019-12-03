package com.es.phoneshop.order;

public interface OrderDao {

    void placeOrder(Order order, String wayOfPayment);

}
