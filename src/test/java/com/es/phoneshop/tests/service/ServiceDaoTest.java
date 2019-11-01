package com.es.phoneshop.tests.service;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import service.Service;

import java.math.BigDecimal;
import java.util.Currency;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ServiceDaoTest {

    private Service serviceDaoTest;

    private Product product;

    private ArrayListProductDao productList;

    @Before
    public void setup() {
        product = new Product("15L", "Siemens SXG75", new BigDecimal(150),
                Currency.getInstance("USD"), 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        serviceDaoTest = serviceDaoTest.getInstance();
        productList = productList.getInstance();
    }

    @Test
    public void testWhenServiceFindProdsNoResults() {
        assertTrue(serviceDaoTest.findProducts().isEmpty());
    }

    @Test
    public void testWhenServiceSaveNoResult() {
        serviceDaoTest.save(product);
        assertTrue(productList.findProducts().contains(product));
    }

    @Test
    public void testWhenServiceDeleteNoResult() {           //?
        serviceDaoTest.delete(product.getId());
        assertFalse(productList.findProducts().contains(product));
    }

    @Test
    public void testWhenServiceGetProductNoResult() {
        setProduct();
        serviceDaoTest.getProduct(product.getId());
        assertTrue(productList.findProducts().contains(product));
    }

    private void setProduct() {
        serviceDaoTest.save(product);
    }
}

