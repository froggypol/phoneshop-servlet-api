package com.es.phoneshop.model.product;

import java.util.Queue;

public class RecentlyViewedProductsModel {

    private Queue<Product> recentlyViewedList;

    public RecentlyViewedProductsModel() {
    }

    public void addProductToViewedList(Product product) {
        if (recentlyViewedList.contains(product)) {
            return;
        }
        if (recentlyViewedList.size() == 3) {
            recentlyViewedList.remove();
        }
        recentlyViewedList.add(product);
    }

    public void setRecentlyViewedList(Queue<Product> recentlyViewedList) {
        this.recentlyViewedList = recentlyViewedList;
    }
}
