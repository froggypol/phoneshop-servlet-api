package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class CustomProductDaoTest {

    private CustomProductDao productDao;

    @Before
    public void setup() {
        productDao = productDao.getInstance();
        productDao.save(new Product("1L", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("2L", "Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("3L", "Samsung Galaxy S III", new BigDecimal(300), Currency.getInstance("USD"), 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
    }

    @Test
    public void testWhenFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testWhenGetProductIdWrongResults() {
        String idToFind = productDao.findProducts().get(0).getId();
        assertEquals(productDao.findProducts().get(0), productDao.getProduct(idToFind).get());
    }

    @Test
    public void testWhenGetNullProductWrongResults() {
        String idToFind = productDao.findProducts().get(0).getId();
        Product expected = productDao.findProducts().get(0);
        assertEquals(expected, productDao.getProduct(idToFind).get());
    }

    @Test
    public void testWhenDeleteProductError() {
        String idToDelete = productDao.findProducts().get(0).getId();
        Product productToDetele = productDao.getProduct(idToDelete).get();
        productDao.delete(idToDelete);
        assertFalse(productDao.findProducts().contains(productToDetele));
    }

    @Test
    public void testWhenSaveProductError() {
        Currency usd = Currency.getInstance("USD");
        Product toSave = new Product("22L", "Siemens2322 SXG75",
                new BigDecimal(150), usd, 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productDao.save(toSave);
        assertTrue(productDao.findProducts().contains(toSave));
    }
}
