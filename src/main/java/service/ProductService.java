package service;

import com.es.phoneshop.model.product.CustomProductDao;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public class ProductService {

    private CustomProductDao productDaoService;

    private static ProductService serviceSingleton;

    private ProductService() {
        productDaoService = productDaoService.getInstance();
    }

    public static ProductService getInstance() {
        if (serviceSingleton == null) {
            serviceSingleton = new ProductService();
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
