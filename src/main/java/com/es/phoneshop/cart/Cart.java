package com.es.phoneshop.cart;

import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                                .map(CartItem::getProductItem)
                                .map(Product::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }



    public void addToCart(int quantity, Product productToAdd, HttpServletRequest request, HttpServletResponse response) {
        CartItem cartItem = new CartItem(quantity, productToAdd);
        //cartItemList = cart.getListCartItem();
        if (quantity > productToAdd.getStock() || productToAdd.getStock() == 0) {
            throw new OutOfStockException();
        }
        if (!cartItemList.contains(cartItem)) {
            addNewCartItem(quantity, cartItem);
            return;
        } else if (cartItemList.contains(cartItem)) {
            CartItem addedItem = cartItemList.get(cartItemList.indexOf(cartItem));
            refreshCartItem(quantity, productToAdd, addedItem, cartItem);
        }
    }

    public void refreshCartItem(int quantity, Product productToAdd, CartItem addedItem, CartItem cartItem) {
        if (quantity <= productToAdd.getStock() - addedItem.getQuantity()) {
            int mew = quantity + addedItem.getQuantity();
            addedItem.setQuantity(mew);
            cartItemList.set(cartItemList.indexOf(cartItem), addedItem);
            setCartItemList(cartItemList);
        } else {
            throw new OutOfStockException();
        }
    }

    public void addNewCartItem(int quantity, CartItem cartItem) {
        cartItem.setQuantity(quantity);
        cartItemList.add(cartItem);
    }

    public void recalculate() {
        int resultQuantity = countQuantity();
        BigDecimal resultCost = countCost();
        setTotalQuantity(resultQuantity);
        setTotalCost(resultCost);
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
