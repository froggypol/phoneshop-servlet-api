package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CustomProductDaoTest {

    private CustomProductDao productDao;

    private List<Product> listProducts = new ArrayList<>();

    @Before
    public void setup() {
        productDao = CustomProductDao.getInstance();
        listProducts = productDao.setProductList();
    }

    @Test
    public void findProductsNoResultsWhenTestCustomProductDao() {
        assertFalse(listProducts.isEmpty());
    }

    @Test
    public void wrongResultForGetProductByIdWhenTestCustomProductDao() {
        String idToFind = productDao.findProducts().get(0).getId();
        assertEquals(listProducts.get(0), productDao.getProduct(idToFind).get());
    }

    @Test
    public void wrongResultForGetNullProductWhenTestCustomProductDao() {
        String idToFind = listProducts.get(0).getId();
        Product expected = listProducts.get(0);
        assertEquals(expected, productDao.getProduct(idToFind).get());
    }

    @Test
    public void notDeletedProductWhenTestCustomProductDao() {
        String idToDelete = listProducts.get(0).getId();
        Product productToDetele = listProducts.get(0);
        productDao.delete(idToDelete);
        assertFalse(productDao.getProductList().contains(productToDetele));
    }

    @Test
    public void notSavedProductWhenTestCustomProductDao() {
        Currency usd = Currency.getInstance("USD");
        Product toSave = new Product("Siemens2322 SXG75",
                new BigDecimal(150), usd, 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productDao.save(toSave);
        assertTrue(productDao.getProductList().contains(toSave));
    }

    @Test
    public void notFoundProductByNameWhenTestCustomProductDao() {
        String productName = listProducts.get(0).getDescription();
        List<Product> compareWith = listProducts.stream().filter(product ->
                product.getDescription().contains(productName)).collect(Collectors.toList());
        assertEquals(productDao.searchFor(productName, null, null),
                compareWith);
    }

    @Test
    public void bugWithProductListSizeWhenTestCusstomProductDao() {
        String orderToSort = "asc";
        String fieldToSort = "desc";
        assertTrue(productDao.searchFor(null, fieldToSort, orderToSort).size()
                == listProducts.size());
    }

    @Test
    public void bugWithProductListSizeWhenTestCusstomProductDaoWithNullParameters() {
        assertTrue(productDao.searchFor(null, null, null).size()
                == listProducts.size());
    }
}
