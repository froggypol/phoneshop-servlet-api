package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Currency;

public class Product {

    private volatile Long id;

    private String description;

    private BigDecimal price;

    private Currency currency;

    private int stock;

    private String imageUrl;

    public Product() {
    }

    public Product(Long id, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
