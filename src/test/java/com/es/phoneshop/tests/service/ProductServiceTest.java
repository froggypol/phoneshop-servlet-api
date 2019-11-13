package com.es.phoneshop.tests.service;

import com.es.phoneshop.custom.exceptions.CustomNoSuchElementException;
import com.es.phoneshop.model.product.CustomProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.Silent.class)

public class ProductServiceTest {

    private ProductService serviceTest;

    private List<Product> listProducts = new ArrayList<>();

    private Product product;

    @Mock
    private CustomProductDao productList = Mockito.mock(CustomProductDao.class);

    @Before
    public void setup() {
        product = new Product("Siemens SXG75", new BigDecimal(150),
                Currency.getInstance("USD"), 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master" +
                        "/manufacturer/Siemens/Siemens%20SXG75.jpg");
        serviceTest = ProductService.getInstance();
        listProducts = new ArrayList<>(Arrays.asList((new Product("Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg")),
                (new Product("Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg")),
                (new Product("Samsung Galaxy S III", new BigDecimal(300), Currency.getInstance("USD"), 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"))));
        serviceTest.setCustomList(listProducts);
    }

    @Test
    public void saveNewProductWhenProductServiceTestSave() {
        serviceTest.save(product);
        listProducts.add(product);
        assertTrue(serviceTest.getCustomProductDao().getProductList().contains(product));
    }

    @Test
    public void removeProductWhenProductServiceTestDelete() {
        serviceTest.delete(product.getId());
        listProducts.remove(product);
        assertFalse(serviceTest.getCustomProductDao().getProductList().contains(product));
    }

    @Test
    public void findCorrectProductWhenProductServiceTestGetProductById() throws CustomNoSuchElementException {
        Product p = serviceTest.getCustomProductDao().getProductList().get(0);
        serviceTest.getProductById(p.getId());
        assertTrue(serviceTest.getCustomProductDao().getProductList().contains(p));
    }

    @Test
    public void findCorrectProductListWhenProductServiceTestFindProducts() {
        Mockito.when(productList.getProductList()).thenReturn(listProducts);
        assertEquals(productList.getProductList(), serviceTest.findProducts(null, null, null));
    }
}
