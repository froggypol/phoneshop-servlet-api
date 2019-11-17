package com.es.phoneshop.cart;

import com.es.phoneshop.model.product.Product;

import java.util.Objects;

public class CartItem {

    private Product productItem;

    private int quantity;

    public CartItem(int quantity, Product product) {
        this.quantity = quantity;
        this.productItem = product;
    }

    public Product getProductItem() {
        return productItem;
    }

    public void setProductItem(Product productItem) {
        this.productItem = productItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return productItem.equals(cartItem.productItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productItem);
    }
}
