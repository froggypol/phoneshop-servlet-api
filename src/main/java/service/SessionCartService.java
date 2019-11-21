package service;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class SessionCartService implements CartService {

    private static SessionCartService cartService;

    private ProductService productService;

    private SessionCartService() {
        productService = ProductService.getInstance();
    }

    public static SessionCartService getInstance() {
        if (cartService == null) {
            cartService = new SessionCartService();
        }
        return cartService;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        return cart;
    }

    @Override
    public int countQuantity(HttpServletRequest request, HttpServletResponse response) {
        return getCart(request).countQuantity();
    }

    @Override
    public BigDecimal countCost(HttpServletRequest request, HttpServletResponse response) {
        return getCart(request).countCost();
    }

    @Override
    public void addToCart(String id, int quantity, HttpServletRequest request, HttpServletResponse response) throws OutOfStockException {
        Cart cart = cartService.getCart(request);
        Product productToAdd = productService.getProductById(id);
        cart.addToCart(quantity, productToAdd);
    }
}
