package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ArrayListProductDao productDao;
    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse("Empty product list, sorry", productDao.findProducts().isEmpty());
    }

    @Test
    public void testGetProductIdWrongResults(){
        long idToFind = 1;
        try {
            Product expected = productDao.productListDaoSynchronized.get((int) idToFind - 1);
            assertEquals(expected, productDao.getProduct(idToFind));
        }
        catch(IndexOutOfBoundsException exc){
            exc.printStackTrace();
        }
    }

    @Test
    public void testDeleteProductError(){
        long id = 1;
        Product productToDetele = productDao.getProduct(id);
        productDao.delete(id);
        assertFalse("Error in deleting element", productDao.productListDaoSynchronized.contains(productToDetele));
    }

    @Test
    public void testSaveProductError(){
        Currency usd = Currency.getInstance("USD");
        Product toSave = new Product((long)productDao.productListDaoSynchronized.size()+1, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        assertFalse("Not found corectly saved product :(", productDao.productListDaoSynchronized.contains(toSave));
    }
}
