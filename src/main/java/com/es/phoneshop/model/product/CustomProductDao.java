package com.es.phoneshop.model.product;

import com.es.phoneshop.custom.exceptions.CustomNoSuchElementException;
import com.es.phoneshop.synchronization.object.SynchronizedObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        Optional<Product> product;
        synchronized (new SynchronizedObject(id)) {
            try {
                product = productListDao.stream()
                        .filter(prod -> prod.getId().equals(id)).findFirst();
                if (product.isPresent()) {
                    return product;
                } else {
                    throw new CustomNoSuchElementException();
                }
            } catch (CustomNoSuchElementException customException) {
                return null;
            }
        }
    }

    @Override
    public List<Product> findProducts() {
        synchronized (this) {
            return productListDao.stream().filter(product -> product.getStock() > 0 && !product.getPrice().equals(BigDecimal.ZERO))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    @Override
    public void save(Product product) {
        synchronized (new SynchronizedObject(product.getId())) {
            productListDao.add(product);
        }
    }

    @Override
    public void delete(String id) {
        synchronized (new SynchronizedObject(id)) {
            productListDao = productListDao.stream().filter(s -> !s.getId().equals(id)).collect(Collectors.toList());
        }
    }

    public List<Product> searchFor(String productName, String fieldToSort, String orderToSort) {
        synchronized (new SynchronizedObject(productName)) {
            Sort sort = new Sort();
            List<Product> res = new ArrayList<>();
            if (productName == null && fieldToSort == null && orderToSort == null) {
                return productListDao;
            } else if (productName != null && !productName.equals("")) {
                String[] list = Pattern.compile(" ").split(productName);
                productListDao.forEach(product -> {
                    IntStream.range(0, list.length).forEach(j -> {
                        if (list[j] == null || product.getDescription().contains(list[j])
                                && !res.contains(product))
                            res.add(product);
                    });
                });
                return res;
            } else {
                return sort.sortProductsList(productListDao, fieldToSort, orderToSort);
            }
        }
    }

    public List<Product> setProductList() {
        productListDao = new ArrayList<>();
        productListDao.add(new Product("Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productListDao.add(new Product("Samsung Galaxy S2", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productListDao.add(new Product("Samsung Galaxy S3", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        return productListDao;
    }

    public List<Product> getProductList() {
        return productListDao;
    }
}
