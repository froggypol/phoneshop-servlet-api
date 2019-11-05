package com.es.phoneshop.model.product;

import com.es.phoneshop.history.price.menu.PriceHistory;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Product {

    private String id;

    private String description;

    private BigDecimal price;

    private Currency currency;

    private int stock;

    private String imageUrl;

    private PriceHistory priceHistory;

    public Product() {
    }

    public Product(String id, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistory = new PriceHistory(this.description);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public void setPriceHistory(PriceHistory priceHistory) {
        this.priceHistory = priceHistory;
    }

    public PriceHistory getPriceHistory() {
        return priceHistory;
    }

    public void setListPrices() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (this == o) return true;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
