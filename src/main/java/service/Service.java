package service;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public class Service {

    private ArrayListProductDao productDaoService;
    
    private static Service serviceSingleton;

    private Service() {
        productDaoService = productDaoService.getInstance();
    }

    public static Service getInstance() {
        if(serviceSingleton == null) {
            serviceSingleton = new Service();
        }
        return serviceSingleton;
    }

    public Product getProduct(String id) {
        return productDaoService.getProduct(id).get();
    }

    public List<Product> findProducts() {
        return productDaoService.findProducts();
    }

    public void save(Product product) {
        productDaoService.save(product);
    }

    public void delete(String id) {
        productDaoService.delete(id);
    }
}
