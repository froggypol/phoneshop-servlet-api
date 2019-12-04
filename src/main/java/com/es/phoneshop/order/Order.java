package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order extends Cart {

    private List<CartItem> cartItemList;

    private BigDecimal subTotal;

    private BigDecimal deliveryCost;

    private String name;

    private String surName;

    private Date date;

    private String address;

    private String phoneNumber;

    private String id;

    public Order(Cart cart) {
        cartItemList = cart.getListCartItem();
        subTotal = cart.getTotalCost();
    }

    public BigDecimal getSubTotal() {
            return subTotal;
    }

    public BigDecimal getDeliveryCost() {
            return deliveryCost;
    }

    public List<CartItem> getListCartItem() {
            return cartItemList;
    }

    public void setSubTotal(BigDecimal subTotal) {
            this.subTotal = subTotal;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
            this.deliveryCost = deliveryCost;
    }

    public String getName() {
            return name;
    }

    public String getSurName() {
            return surName;
    }

    public Date getDate() {
            return date;
    }

    public String getAddress() {
            return address;
    }

    public String getPhoneNumber() {
            return phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
            this.name = name;
    }

    public void setSurName(String surName) {
            this.surName = surName;
    }

    public void setDate(Date date) {
            this.date = date;
    }

    public void setAddress(String address) {
            this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
