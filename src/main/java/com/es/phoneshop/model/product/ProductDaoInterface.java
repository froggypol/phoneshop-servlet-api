package com.es.phoneshop.model.product;

import java.util.List;
import java.util.Optional;

public interface ProductDaoInterface {

    Optional<Product> getProductById(String id);

    List<Product> findProducts();

    void save(Product product);

    void delete(String id);
}
