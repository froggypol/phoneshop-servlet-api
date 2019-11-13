package com.es.phoneshop.model.product;

import com.es.phoneshop.synchronization.object.SynchronizedObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CustomProductDao implements ProductDaoInterface {

    private static CustomProductDao productDao;

    private static List<Product> productList;

    private CustomProductDao() { }

    private static void fillProductList() {
        Currency usd = Currency.getInstance("USD");
        productList = new ArrayList<>();
        productList.add(new Product("Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productList.add(new Product("Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productList.add(new Product("Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productList.add(new Product("Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        productList.add(new Product("Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        productList.add(new Product("HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        productList.add(new Product("Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        productList.add(new Product("Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        productList.add(new Product("Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        productList.add(new Product("Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        productList.add(new Product("Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        productList.add(new Product("Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        productList.add(new Product("Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }

    public static CustomProductDao getInstance() {
        if (productDao == null) {
            productDao = new CustomProductDao();
            fillProductList();
        }
        return productDao;
    }

    @Override
    public Optional<Product> getProductById(String id) {
        synchronized (SynchronizedObject.getSynchronizedObject(id)) {
                return productList.stream()
                                  .filter(prod -> prod.getId().equals(id))
                                  .findAny();
        }
    }

    @Override
    public List<Product> findProducts() {
            return productList.stream()
                              .filter(product -> product.getStock() > 0 && !product.getPrice().equals(BigDecimal.ZERO))
                              .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        synchronized (SynchronizedObject.getSynchronizedObject(product.getId())) {
            productList.add(product);
        }
    }

    @Override
    public void delete(String id) {
        synchronized (SynchronizedObject.getSynchronizedObject(id)) {
            productList = productList.stream()
                                     .filter(product -> !product.getId().equals(id))
                                     .collect(Collectors.toList());
        }
    }

    public List<Product> searchFor(String productName, String fieldToSort, String orderToSort) {
            Sort sort = new Sort(productList);
            List<Product> res = new ArrayList<>();
            if (productName == null && fieldToSort == null && orderToSort == null) {
                return productList;
            } else if (productName != null && !productName.equals("")) {
                String[] list = Pattern.compile(" ").split(productName);
                productList.forEach(product -> {
                    Arrays.stream(list).forEach(word -> {
                        if (word == null || product.getDescription().contains(word) && !res.contains(product)) {
                            res.add(product);
                        }
                    });
                });
                return res;
            } else {
                return sort.sortProductsList(fieldToSort, orderToSort);
            }
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> toSet) {
        productList = toSet;
    }
}
