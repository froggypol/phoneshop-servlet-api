package service;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SessionCartService implements CartService {

    private Cart cart;

    private static SessionCartService cartService;

    private List<CartItem> cartItemList;

    private ProductService productService;

    private CartItem cartItem;

    private HttpSession session;

    private SessionCartService() {
        cartItemList = new ArrayList<>();
        productService = ProductService.getInstance();
    }

    public static SessionCartService getInstance() {
        if (cartService == null) {
            cartService = new SessionCartService();
        }
        return cartService;
    }

    @Override
    public Cart getCart(HttpServletRequest request, HttpServletResponse response) {
        session = request.getSession();
        cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        setCart(cart, request, response);
        return cart;
    }

    @Override
    public int countQuantity() {
        return cart.countQuantity();
    }

    @Override
    public BigDecimal countCost() {
        return cart.countCost();
    }

    @Override
    public void addToCart(String id, int quantity, HttpServletRequest request, HttpServletResponse response) throws OutOfStockException {
        cart = cartService.getCart(request, response);
        Product productToAdd = productService.getProductById(id);
        cart.addToCart(quantity, productToAdd, request, response);
        cart.recalculate();
    }

    public void setCart(Cart cart, HttpServletRequest request, HttpServletResponse response) {
        session = request.getSession();
        session.setAttribute("cart", cart);
    }

    public void setCartList(List<CartItem> list) {
        cart.setCartItemList(list);
    }
}
