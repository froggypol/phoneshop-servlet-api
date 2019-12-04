package com.es.phoneshop.cart;

import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import validation.ErrorMap;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cart implements Serializable {

    private List<CartItem> cartItemList;

    private int totalQuantity;

    private BigDecimal totalCost;

    public Cart() {
        cartItemList = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
    }

    public void addToCart(int quantity, Product productToAdd, ErrorMap errorMap) {
        CartItem cartItem = new CartItem(quantity, productToAdd);
        if (quantity > productToAdd.getStock() || productToAdd.getStock() == 0) {
            throw new OutOfStockException();
        }
        if (!cartItemList.contains(cartItem)) {
            addNewCartItem(cartItem);
        } else if (cartItemList.contains(cartItem)) {
            CartItem addedItem = cartItemList.get(cartItemList.indexOf(cartItem));
            refreshCartItem(quantity, productToAdd, addedItem, cartItem, errorMap);
        }
        recalculate();
    }

    private void refreshCartItem(int quantity, Product productToAdd, CartItem addedItem, CartItem cartItem, ErrorMap errorMap) {
        if (quantity <= productToAdd.getStock() - addedItem.getQuantity() || quantity == productToAdd.getStock()) {
            addedItem.setQuantity(quantity);
            cartItemList.set(cartItemList.indexOf(cartItem), addedItem);
        } else {
            errorMap.customException("quantity&" + productToAdd.getId(), "Not enough product in stock. Available " + productToAdd.getStock());
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

    public void updateCart(int quantity, Product productToAdd, CartItem addedItem, CartItem cartItem, ErrorMap errorMap) {
        refreshCartItem(quantity, productToAdd, addedItem, cartItem, errorMap);
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
                                        ((bigDecimal, cartItem) -> {
                                        BigDecimal totalCost = cartItem.getCost().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                                        return bigDecimal.add(totalCost);
                                        }), BigDecimal::add);
    }

    public List<CartItem> deleteCartItem(String idToDelete) {
        Optional<CartItem> toDelete = cartItemList.stream()
                                                  .filter(cartItem -> cartItem.getProductItem().getId().equals(idToDelete))
                                                  .findAny();
        CartItem toDeleteCartItem = toDelete.get();
        cartItemList.remove(toDeleteCartItem);
        recalculate();
        return cartItemList;
    }

    public void resetOrder() {
        cartItemList = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
        totalQuantity = 0;
    }

    public List<CartItem> getListCartItem() {
        return cartItemList;
    }

    public List<Product> getProductList() {
        return  cartItemList.stream()
                            .map(CartItem::getProductItem)
                            .collect(Collectors.toList());
    }

    public CartItem getCartItem (Product product) {
        return getListCartItem().stream()
                                .filter(cartItem -> cartItem.getProductItem().getId().equals(product.getId())).findAny().get();
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
