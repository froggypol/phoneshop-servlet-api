package com.es.phoneshop.cart;

import com.es.phoneshop.custom.exceptions.OutOfStockException;
import validation.ErrorMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public interface CartService {

    Cart getCart(HttpServletRequest request);

    void addToCart(String id, int quantity, ErrorMap errorMap, HttpServletRequest request, HttpServletResponse response) throws OutOfStockException;

    int countQuantity(HttpServletRequest request, HttpServletResponse response);

    BigDecimal countCost(HttpServletRequest request, HttpServletResponse response);
}
