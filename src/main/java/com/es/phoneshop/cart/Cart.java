package com.es.phoneshop.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> cartItemList;

    private int totalQuantity;

    private BigDecimal totalCost;

    public Cart() {
        cartItemList = new ArrayList<>();
    }

    public Cart(List<CartItem> listCartItem) {
        this.cartItemList = listCartItem;
    }

    public List<CartItem> getListCartItem() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
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
}
