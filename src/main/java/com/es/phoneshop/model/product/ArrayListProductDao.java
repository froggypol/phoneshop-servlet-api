package com.es.phoneshop.model.product;

import com.es.phoneshop.CustomExceptions.CustomNoSuchElementException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class ArrayListProductDao implements ProductDao {

    private static ArrayListProductDao productDaoSingleton;

    private static List<Product> productListDao;

    private ArrayListProductDao() {
    }

    public static ArrayListProductDao getInstance() {
        if(productDaoSingleton == null) {
            productDaoSingleton = new ArrayListProductDao();
            productListDao = new ArrayList<>();
        }
        return productDaoSingleton;
    }

    @Override
    public Optional<Product> getProduct(String id) {
        Optional<Product> product;
        product = productListDao.stream()
                                .filter(prod -> prod.getId().equals(id)).findFirst();
        if(product.isPresent()) {
            return product;
        }
        try {
            throw new CustomNoSuchElementException();
        }
        catch(CustomNoSuchElementException customException){
            log.print("Exception in getProduct()");
        }
        return product;
    }

    @Override
    public List<Product> findProducts() {
        productListDao.stream().filter(product -> product.getStock() > 0 && !product.getPrice().equals(BigDecimal.ZERO))
                      .collect(Collectors.toCollection(ArrayList::new));
        return productListDao;
    }

    @Override
    public void save(Product product) {
        if (productListDao.stream()
                          .filter(prod -> prod.getId().equals(product.getId())).findFirst()
                          .isPresent()) {
            long index = productListDao.indexOf(product);
            productListDao.add((int)index, product);
        } else {
            UUID uuid = UUID.randomUUID();
            product.setId(uuid.toString());
            productListDao.add(product);
        }
    }

    @Override
    public void delete(String id) {
        productListDao = productListDao.stream().filter(s -> !s.getId().equals(id)).collect(Collectors.toList());
    }

    public ArrayList<Product> searchFor(String productName) {
        return productListDao.stream()
                             .filter(product -> product.getDescription().contains(productName))
                             .collect(Collectors.toCollection(ArrayList::new));
    }
}
