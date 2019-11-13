package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CustomProductDaoTest {

    private CustomProductDao customProductDao;

    private List<Product> listProducts = new ArrayList<>();

    @Before
    public void setup() {
        customProductDao = CustomProductDao.getInstance();
        listProducts = new ArrayList<>(Arrays.asList(
                new Product("Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"),
                new Product("Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"),
                new Product("Samsung Galaxy S III", new BigDecimal(300), Currency.getInstance("USD"), 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg")));
        customProductDao.setProductList(listProducts);
    }

    @Test
    public void notEmptyListWhenCustomProductDaoTestGetProductList() {
        assertFalse(customProductDao.getProductList().isEmpty());
    }

    @Test
    public void getExpectedProductByIdWhenCustomProductDaoTestGetProductById() {
        String idToFind = listProducts.get(0).getId();
        Product expected = listProducts.get(0);
        assertEquals(expected, customProductDao.getProductById(idToFind).get());
    }

    @Test
    public void deleteProductCorrectlyWhenCustomProductDaoTestDelete() {
        String idToDelete = listProducts.get(0).getId();
        Product productToDetele = listProducts.get(0);
        customProductDao.delete(idToDelete);
        assertFalse(customProductDao.getProductList().contains(productToDetele));
    }

    @Test
    public void saveProductCorrectlyWnenCustomProductDaoTestSave() {
        Currency usd = Currency.getInstance("USD");
        Product toSave = new Product("Siemens2322 SXG75",
                new BigDecimal(150), usd, 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        customProductDao.save(toSave);
        assertTrue(customProductDao.getProductList().contains(toSave));
    }

    @Test
    public void correctResultForSearchingByNameWhenCustomProductDaoTestSearchFor() {
        String productName = listProducts.get(0).getDescription();
        List<Product> compareWith = listProducts.stream()
                                                .filter(product -> product.getDescription().contains(productName))
                                                .collect(Collectors.toList());
        assertEquals(compareWith, customProductDao.searchFor(productName, null, null));
    }

    @Test
    public void resultForSortingWhenCustomProductDaoTestSearchFor() {
        String orderToSort = "asc";
        String fieldToSort = "desc";
        assertTrue(customProductDao.searchFor(null, fieldToSort, orderToSort).size()
                == listProducts.size());
    }

    @Test
    public void correctResultForSearchingWhenCustomProductDaoTestSearchFor() {
        assertEquals(listProducts, customProductDao.searchFor(null, null, null));
    }
}
