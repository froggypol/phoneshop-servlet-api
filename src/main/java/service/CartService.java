package service;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.custom.exceptions.OutOfStockException;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public interface CartService {

    Cart getCart(HttpSession session);

    void addToCart(String id, int quantity, HttpSession session) throws OutOfStockException;

    int countQuantity();

    BigDecimal countCost();
}
