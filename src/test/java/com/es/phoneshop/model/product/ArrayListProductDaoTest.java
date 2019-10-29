package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ArrayListProductDao productDao;
    private List<Product> products;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        products = new ArrayList<>(productDao.findProducts());
    }

    @Test
    public void testWhenFindProductsNoResults() {
        assertFalse(products.isEmpty());
    }

    @Test
    public void testWhenGetProductIdWrongResults() {
        long idToFind = 1;
        Product expected = products.get((int) idToFind - 1);
        assertEquals(expected, productDao.getProduct(idToFind).get());
    }

    @Test
    public void testWhenDeleteProductError() {
        long id = 1;
        Product productToDetele = productDao.getProduct(id).get();
        productDao.delete(id);
        assertFalse(productDao.findProducts().contains(productToDetele));
    }

    @Test
    public void testWhenSaveProductError() {
        Currency usd = Currency.getInstance("USD");
        Product toSave = new Product(15L, "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productDao.save(toSave);
        assertTrue(productDao.findProducts().contains(toSave));
    }
}
