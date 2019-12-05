package com.es.phoneshop.model.product;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.custom.exceptions.CustomNoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Product getProductById(String id) throws CustomNoSuchElementException {
        Optional<Product> product = productDaoService.getProductById(id);
        if (product.isPresent())
            return product.get();
        else {
            throw new CustomNoSuchElementException();
        }
    }

    public List<Product> findProducts() {
        return productDaoService.findProducts();
    }

    public List<Product> findProducts(String productName, String fieldToSort, String orderToSort) {
        return productDaoService.searchFor(productName, fieldToSort, orderToSort);
    }

    public void updateProductAfterOrder(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        setCustomList(returnUpdatedProductList(cart));
    }

    private List<Product> returnUpdatedProductList(Cart cart) {
        return productDaoService.findProducts()
                                .stream()
                                .map(product -> {
                                    if (cart.getProductList().contains(product)) {
                                        Product productInCart = cart.getCartItem(product).getProductItem();
                                        CartItem cartItem = cart.getCartItem(productInCart);
                                        if (cartItem != null) {
                                            int index = cart.getListCartItem().indexOf(cartItem);
                                            product.setStock(product.getStock() - cart.getListCartItem().get(index).getQuantity());
                                        }
                                    }
                                return product;
                                })
                                .collect(Collectors.toList());
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
