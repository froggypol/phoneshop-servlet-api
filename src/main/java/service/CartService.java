package service;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.custom.exceptions.OutOfStockException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public interface CartService {

    Cart getCart(HttpServletRequest request, HttpServletResponse response);

    void addToCart(String id, int quantity, HttpServletRequest request, HttpServletResponse response) throws OutOfStockException;

    int countQuantity();

    BigDecimal countCost();
}
