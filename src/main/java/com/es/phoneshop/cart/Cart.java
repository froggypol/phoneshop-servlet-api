package com.es.phoneshop.cart;

import com.es.phoneshop.model.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Cart {

    private List<CartItem> cartItemList;

    private Queue<Product> recentlyViewedProducts;

    private int totalQuantity;

    private BigDecimal totalCost;

    public Cart() {
        cartItemList = new ArrayList<>();
        recentlyViewedProducts = new LinkedList<>();
    }

    public Cart(List<CartItem> listCartItem) {
        this.cartItemList = listCartItem;
    }

    public List<CartItem> getListCartItem() {
        return cartItemList;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Queue<Product> getRecentlyViewedProducts() {
        return recentlyViewedProducts;
    }
}
