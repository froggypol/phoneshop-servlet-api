package service;

import com.es.phoneshop.model.product.CustomProductDao;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public class Service {

    private CustomProductDao productDaoService;

    private static Service serviceSingleton;

    private Service() {
        productDaoService = productDaoService.getInstance();
    }

    public static Service getInstance() {
        if (serviceSingleton == null) {
            serviceSingleton = new Service();
        }
        return serviceSingleton;
    }

    public Product getProduct(String id) {
        synchronized (id) {
            return productDaoService.getProduct(id).get();
        }
    }

    public List<Product> findProducts() {
        synchronized (this) {
            return productDaoService.findProducts();
        }
    }

    public List<Product> findProducts(String productName, String fieldToSort, String orderToSort) {
        synchronized (this) {
            return productDaoService.searchFor(productName, fieldToSort, orderToSort);
        }
    }

    public void save(Product product) {
        synchronized (product) {
            productDaoService.save(product);
        }
    }

    public void delete(String id) {
        synchronized (id) {
            productDaoService.delete(id);
        }
    }
}
