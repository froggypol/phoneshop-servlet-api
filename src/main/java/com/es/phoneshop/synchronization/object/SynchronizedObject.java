package com.es.phoneshop.synchronization.object;

import com.es.phoneshop.model.product.Product;

import java.util.HashMap;
import java.util.Map;

public class SynchronizedObject {

    private Map<String, Object> productPool = new HashMap<>();

    public SynchronizedObject(String idToCheck) {
        Product product;
        if (productPool.containsKey(idToCheck)) {
            returnIdFromPool(idToCheck);
        } else {
            product = new Product();
            createId(product);
        }
    }

    private String returnIdFromPool(String id) {
        return id;
    }

    private String createId(Product product) {
        productPool.put(product.getId(), product);
        return product.getId();
    }
}
