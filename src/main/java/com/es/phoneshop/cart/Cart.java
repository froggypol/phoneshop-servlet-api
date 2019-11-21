package com.es.phoneshop.cart;

import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

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

    public int countQuantity() {
        return getListCartItem().stream()
                                .mapToInt(CartItem::getQuantity)
                                .sum();
    }

    public BigDecimal countCost() {
        return getListCartItem().stream()
                                .reduce(BigDecimal.ZERO, ((bigDecimal, cartItem) -> bigDecimal.add(cartItem.getCost())), BigDecimal::add);
    }



    public void addToCart(int quantity, Product productToAdd) {
        CartItem cartItem = new CartItem(quantity, productToAdd);
        if (quantity > productToAdd.getStock() || productToAdd.getStock() == 0) {
            throw new OutOfStockException();
        }
        if (!cartItemList.contains(cartItem)) {
            addNewCartItem(cartItem);
            return;
        } else if (cartItemList.contains(cartItem)) {
            CartItem addedItem = cartItemList.get(cartItemList.indexOf(cartItem));
            refreshCartItem(quantity, productToAdd, addedItem, cartItem);
        }
        recalculate();
    }

    private void refreshCartItem(int quantity, Product productToAdd, CartItem addedItem, CartItem cartItem) {
        if (quantity <= productToAdd.getStock() - addedItem.getQuantity()) {
            addedItem.setQuantity(quantity + addedItem.getQuantity());
            cartItemList.set(cartItemList.indexOf(cartItem), addedItem);
        } else {
            throw new OutOfStockException();
        }
    }

    private void addNewCartItem(CartItem cartItem) {
        cartItemList.add(cartItem);
    }

    private void recalculate() {
        int resultQuantity = countQuantity();
        BigDecimal resultCost = countCost();
        setTotalQuantity(resultQuantity);
        setTotalCost(resultCost);
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

    private void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    private void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
