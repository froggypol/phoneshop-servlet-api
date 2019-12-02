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

    protected void recalculate(String way) {
        synchronized (this) {
            if (way != null) {
                setSubTotal(subTotal.add(getDeliveryCost(way)));
            }
        }
    }

    public BigDecimal getSubTotal() {
        synchronized (this) {
            return subTotal;
        }
    }

    private BigDecimal getDeliveryCost(String way) {
        synchronized (this) {
            if (way.equals("cash")) {
                deliveryCost = new BigDecimal(15);
            } else if (way.equals("creditCard")) {
                deliveryCost = new BigDecimal(5);
            }
            return deliveryCost;
        }
    }

    public BigDecimal getDeliveryCost() {
        synchronized (this) {
            return deliveryCost;
        }
    }

    public List<CartItem> getListCartItem() {
        synchronized (this) {
            return cartItemList;
        }
    }

    public void setSubTotal(BigDecimal subTotal) {
        synchronized (this) {
            this.subTotal = subTotal;
        }
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        synchronized (this) {
            this.deliveryCost = deliveryCost;
        }
    }

    public String getName() {
        synchronized (this) {
            return name;
        }
    }

    public String getSurName() {
        synchronized (this) {
            return surName;
        }
    }

    public Date getDate() {
        synchronized (this) {
            return date;
        }
    }

    public String getAddress() {
        synchronized (this) {
            return address;
        }
    }

    public String getPhoneNumber() {
        synchronized (this) {
            return phoneNumber;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        synchronized (this) {
            this.name = name;
        }
    }

    public void setSurName(String surName) {
        synchronized (this) {
            this.surName = surName;
        }
    }

    public void setDate(Date date) {
        synchronized (this) {
            this.date = date;
        }
    }

    public void setAddress(String address) {
        synchronized (this) {
            this.address = address;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        synchronized (this) {
            this.phoneNumber = phoneNumber;
        }
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
