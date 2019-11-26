package com.es.phoneshop.cart;

import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart implements Serializable {

    private List<CartItem> cartItemList;

    private int totalQuantity;

    private BigDecimal totalCost;

    public Cart() {
            cartItemList = new ArrayList<>();
            totalCost = BigDecimal.ZERO;
    }

    public void addToCart(int quantity, Product productToAdd) {
        CartItem cartItem = new CartItem(quantity, productToAdd);
        if (quantity > productToAdd.getStock() || productToAdd.getStock() == 0) {
            throw new OutOfStockException();
        }
        if (!cartItemList.contains(cartItem)) {
            addNewCartItem(cartItem);
            recalculate();
            return;
        } else if (cartItemList.contains(cartItem)) {
            CartItem addedItem = cartItemList.get(cartItemList.indexOf(cartItem));
            refreshCartItem(quantity, productToAdd, addedItem, cartItem);
        }
        recalculate();
    }

    public void refreshCartItem(int quantity, Product productToAdd, CartItem addedItem, CartItem cartItem) {
        if (quantity <= productToAdd.getStock() - addedItem.getQuantity()) {
            addedItem.setQuantity(quantity + addedItem.getQuantity());
            cartItemList.set(cartItemList.indexOf(cartItem), addedItem);
        } else {
            throw new OutOfStockException();
        }
    }

    private void addNewCartItem(CartItem cartItem)
    {
        cartItemList.add(cartItem);
    }

    private void recalculate() {
        int resultQuantity = countQuantity();
        BigDecimal resultCost = countCost();
        setTotalQuantity(resultQuantity);
        setTotalCost(resultCost);
    }

    public void updateCart(int quantity, Product productToAdd, CartItem addedItem, CartItem cartItem) {
        refreshCartItem(quantity, productToAdd, addedItem, cartItem);
        recalculate();
    }

    public int countQuantity() {
        return getListCartItem().stream()
                                .mapToInt(CartItem::getQuantity)
                                .sum();
    }

    public BigDecimal countCost() {
        return getListCartItem().stream()
                                .reduce(BigDecimal.ZERO,
                                        ((bigDecimal, cartItem) -> bigDecimal.add(cartItem.getCost().multiply(BigDecimal.valueOf(cartItem.getQuantity())))),
                                        BigDecimal::add);
    }

    public List<CartItem> deleteCartItem(String idToDelete) {
        Optional<CartItem> toDelete = cartItemList.stream()
                                                  .filter(cartItem -> cartItem.getProductItem().getId().equals(idToDelete))
                                                  .findAny();
        CartItem toDeleteCartItem = toDelete.get();
        setTotalCost(totalCost.subtract(toDeleteCartItem.getCost().multiply(new BigDecimal(toDeleteCartItem.getQuantity()))));
        toDeleteCartItem.setQuantity(0);
        cartItemList.remove(toDeleteCartItem);
        return cartItemList;
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

    public BigDecimal getTotalCost() {
        return totalCost;
    }
}
