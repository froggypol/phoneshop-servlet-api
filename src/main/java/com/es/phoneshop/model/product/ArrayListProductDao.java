package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> productListDao;

    public ArrayListProductDao() {
//        DaoProductListListener productListListener = new DaoProductListListener();
//        productListListener.getInstananse();
            productListDao = new ArrayList<>();
            Currency usd = Currency.getInstance("USD");
            productListDao.add(new Product(1L, "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
            productListDao.add(new Product(2L, "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
            productListDao.add(new Product(3L, "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
            productListDao.add(new Product(4L,"Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
            productListDao.add(new Product(5L,  "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
            productListDao.add(new Product(6L, "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
            productListDao.add(new Product(7L,"Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
            productListDao.add(new Product(8L,"Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
            productListDao.add(new Product(9L,"Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
            productListDao.add(new Product(10L,"Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
            productListDao.add(new Product(11L, "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
            productListDao.add(new Product(12L, "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
            productListDao.add(new Product(13L, "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
        }

    @Override
    public Optional<Product> getProduct(Long id) {
        Optional<Product> product;
        product = productListDao.stream().filter(prod -> prod.getId().equals(id)).findFirst();
        return product;
    }

    @Override
    public List<Product> findProducts() {
        productListDao.stream().filter(product -> product.getStock() > 0 && !product.getPrice().equals(0))
                               .collect(Collectors.toCollection(ArrayList::new));
        return productListDao;
    }

    @Override
    public void save(Product product) {
        if (productListDao.stream().filter(prod -> prod.getId().equals(product.getId())).findFirst().isPresent()){                 // rewriting product
            long index = productListDao.indexOf(product);
            productListDao.add((int)index, product);
        }
        else{
            UUID uuid = UUID.randomUUID();
            product.setId(uuid.getMostSignificantBits());
            productListDao.add(product);
        }
    }

    @Override
    public void delete(Long id) {
        productListDao.stream().filter(s -> s.getId().compareTo(id) != 0).collect(Collectors.toList());
    }

    public ArrayList<Product> searchFor(String productName){
        return productListDao.stream().filter(product -> product.getDescription().contains(productName))
                      .collect(Collectors.toCollection(ArrayList::new));
    }
}
