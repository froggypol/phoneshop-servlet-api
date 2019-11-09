package com.es.phoneshop.tests.service;

import com.es.phoneshop.model.product.CustomProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import service.ProductService;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ProductServiceDaoTest {

    private ProductService serviceDaoTest;

    private Product product;

    private CustomProductDao productList;

    @Before
    public void setup() {
        product = new Product("Siemens SXG75", new BigDecimal(150),
                Currency.getInstance("USD"), 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master" +
                        "/manufacturer/Siemens/Siemens%20SXG75.jpg");
        serviceDaoTest = ProductService.getInstance();
        productList = CustomProductDao.getInstance();
    }

    @Test
    public void emptyProductListWhenTestProductServiceFindProducts() {
        assertTrue(!serviceDaoTest.findProducts().isEmpty());
    }

    @Test
    public void saveNoResultWhenTestServiceProduct() {
        serviceDaoTest.save(product);
        assertTrue(productList.getProductList().contains(product));
    }

    @Test
    public void testWhenServiceDeleteNoResult() {
        serviceDaoTest.delete(product.getId());
        assertFalse(productList.getProductList().contains(product));
    }

    @Test
    public void noResultGetProductByIdWhenTestServiceProduct() {
        productList.getProductList().add(product);
        serviceDaoTest.getProduct(product.getId());
        assertTrue(productList.getProductList().contains(product));
    }

    @Test
    public void bugWithProductListDaoWhenTestSearchForInServiceProduct() {
        assertEquals(serviceDaoTest.findProducts(null, null, null).size(),
                productList.getProductList().size());
    }

    @Test
    public void bugWithSearchProductByNameWhenTestServiceProduct() {
        productList.getProductList().add(product);
        assertEquals(serviceDaoTest.findProducts(product.getDescription(), null, null),
                productList.getProductList().stream().filter(product1 -> product1.getDescription()
                        .contains(product.getDescription())).collect(Collectors.toList()));
    }
}
