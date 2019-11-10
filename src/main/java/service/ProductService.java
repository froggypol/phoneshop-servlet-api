package service;

import com.es.phoneshop.model.product.CustomProductDao;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public class ProductService {

    private CustomProductDao productDaoService;

    private static ProductService serviceSingleton;

    private ProductService() {
        productDaoService = CustomProductDao.getInstance();
    }

    public static ProductService getInstance() {
        if (serviceSingleton == null) {
            serviceSingleton = new ProductService();
        }
        return serviceSingleton;
    }

    public Product getProductById(String id) throws NullPointerException {
        return productDaoService.getProductById(id).get();
    }

    public List<Product> findProducts() {
        return productDaoService.findProducts();
    }

    public List<Product> findProducts(String productName, String fieldToSort, String orderToSort) {
        return productDaoService.searchFor(productName, fieldToSort, orderToSort);
    }

    public void save(Product product) {
        productDaoService.save(product);
    }

    public void delete(String id) {
        productDaoService.delete(id);
    }

    public CustomProductDao getCustomProductDao() {
        return productDaoService;
    }

    public void setCustomList(List<Product> toSet) {
        productDaoService.setProductList(toSet);
    }
}
