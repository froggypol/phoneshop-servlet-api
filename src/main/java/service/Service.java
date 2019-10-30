package service;

import com.es.phoneshop.model.product.ArrayListProductDao;

public class Service {

    private ArrayListProductDao productDaoService;


    public ArrayListProductDao getProductDaoService() {
        productDaoService = new ArrayListProductDao();
        return productDaoService;
    }
}
