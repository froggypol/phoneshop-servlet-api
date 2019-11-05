package com.es.phoneshop.model.product;

import com.es.phoneshop.CustomExceptions.CustomNoSuchElementException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class CustomProductDao implements ProductDao {

    private static CustomProductDao productDaoSingleton;

    private static List<Product> productListDao;

    private CustomProductDao() {
    }

    public static CustomProductDao getInstance() {
        if (productDaoSingleton == null) {
            productDaoSingleton = new CustomProductDao();
            productListDao = new ArrayList<>();
        }
        return productDaoSingleton;
    }

    @Override
    public Optional<Product> getProduct(String id) {
        synchronized (id) {
            Optional<Product> product;
            product = productListDao.stream()
                    .filter(prod -> prod.getId().equals(id)).findFirst();
            if (product.isPresent()) {
                return product;
            }
            try {
                throw new CustomNoSuchElementException();
            } catch (CustomNoSuchElementException customException) {
                log.print("Exception in getProduct()");
            }
            return product;
        }
    }

    @Override
    public List<Product> findProducts() {
        synchronized (this) {
            productListDao.stream().filter(product -> product.getStock() > 0 && !product.getPrice().equals(BigDecimal.ZERO))
                    .collect(Collectors.toCollection(ArrayList::new));
            return productListDao;
        }
    }

    @Override
    public void save(Product product) {
        synchronized (product) {
            if (productListDao.stream()
                    .filter(prod -> prod.getId().equals(product.getId())).findFirst()
                    .isPresent()) {
                long index = productListDao.indexOf(product);
                productListDao.add((int) index, product);
            } else {
                UUID uuid = UUID.randomUUID();
                product.setId(uuid.toString());
                productListDao.add(product);
            }
        }
    }

    @Override
    public void delete(String id) {
        synchronized (id) {
            productListDao = productListDao.stream().filter(s -> !s.getId().equals(id)).collect(Collectors.toList());
        }
    }

    public List<Product> searchFor(String productName, String fieldToSort, String orderToSort) {
        synchronized (this) {
            List<Product> res = new ArrayList<>();
            if (productName == null) {
                return productListDao;
            } else {
                String[] list = Pattern.compile(" ").split(productName);
                productListDao.stream().forEach(product -> {
                    IntStream.range(0, list.length).forEach(j -> {
                        System.out.println(list[j]);
                        if (list[j] == null || product.getDescription().contains(list[j])
                                && !res.contains(product))
                            res.add(product);
                    });
                });
            }
            if (fieldToSort != null && orderToSort != null) {
                Sort sort = new Sort();
                return sort.sortProductsList(res, fieldToSort, orderToSort);
            }
            return res;
        }
    }
}
