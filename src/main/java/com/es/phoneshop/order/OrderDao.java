package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

public interface OrderDao {

    Order placeOrder(Cart cart, HttpServletRequest request) throws ParseException;

}
