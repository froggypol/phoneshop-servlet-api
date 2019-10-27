package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    List<Product> productListDao;
    List<Product> productListDaoSynchronized;
    public ArrayListProductDao() {
        productListDao = new ArrayList<>();
        productListDaoSynchronized = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        save(new Product((long)productListDao.size()+1, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        save(new Product((long)productListDao.size()+1, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        save(new Product((long)productListDao.size()+1, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        save(new Product((long)productListDao.size()+1, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        save(new Product((long)productListDao.size()+1, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        save(new Product((long)productListDao.size()+1, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        save(new Product((long)productListDao.size()+1, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        save(new Product((long)productListDao.size()+1, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        save(new Product((long)productListDao.size()+1, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        save(new Product((long)productListDao.size()+1, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        save(new Product((long)productListDao.size()+1, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        save(new Product((long)productListDao.size()+1, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        save(new Product((long)productListDao.size()+1, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
        productListDaoSynchronized.addAll(Collections.synchronizedList(productListDao));
    }

    @Override
    public Product getProduct(Long id){
        Product product = new Product();
            try {
                product = productListDaoSynchronized.stream()
                        .filter(prod -> prod.getId() == id)
                        .findFirst().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return product;
    }

    @Override
    public List<Product> findProducts() {
        ArrayList<Product> result = new ArrayList<>();
         result.addAll(productListDaoSynchronized.stream()
                .filter(product -> product.getStock() > 0 && !product.getPrice().equals(0))
                 .collect(Collectors.toCollection(ArrayList::new)));
         return result;
    }

    @Override
    public void save(Product product) {
        product.setId((long)productListDaoSynchronized.size()+1);
        productListDaoSynchronized.add(product);
    }

    @Override
    public void delete(Long id) {
        List<Product> result = new ArrayList<>();
        result.addAll(productListDaoSynchronized.stream().filter(product -> product.getId() != id)
                .collect(Collectors.toList()));
        productListDaoSynchronized.clear();
        productListDaoSynchronized.addAll(result);
    }

    public List<Product> getProductListDao() {
        return productListDaoSynchronized;
    }
}
