package com.es.phoneshop.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.Queue;

public class RecentlyViewedProductsService {

    private Queue<Product> recentlyViewedProducts;


    public void setRecentlyViewedProducts(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("recentlyViewedProducts", recentlyViewedProducts);
    }

    public void addProductToViewedList(Product product, HttpServletRequest request, HttpServletResponse response) {
        if (recentlyViewedProducts == null) {
            recentlyViewedProducts = new LinkedList<>();
            recentlyViewedProducts.add(product);
        } else {
            if (recentlyViewedProducts.contains(product))
                return;
            if (recentlyViewedProducts.size() == 3) {
                recentlyViewedProducts.remove();
            }
            recentlyViewedProducts.add(product);
        }
    }

    public Queue<Product> getRecentlyViewedProducts(HttpServletRequest request, HttpServletResponse response) {
        recentlyViewedProducts = (Queue<Product>) request.getSession().getAttribute("recentlyViewedProducts");
        return  recentlyViewedProducts;
    }
}
