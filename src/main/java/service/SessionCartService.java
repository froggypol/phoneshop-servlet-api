package service;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.utils.UtilParse;
import validation.CustomValidation;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
        Cart cart = (Cart) request.getSession().getAttribute("cart");
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

    public void updateCart(List<String> quantityList, HttpServletRequest request) throws ParseException {
        Cart cart = getCart(request);
        List<Integer> quantityValueList = new ArrayList<>();
        for (int i = 0; i < quantityList.size(); i++) {
            quantityValueList.add(UtilParse.parseIntByLocale(request.getLocale(), quantityList.get(i)));
            int quantityToRefresh = quantityValueList.get(i);
            CartItem cartItemToRefresh = cart.getListCartItem().get(i);
            if (Math.abs(quantityToRefresh - cartItemToRefresh.getQuantity()) > 0) {
                cart.updateCart(quantityToRefresh, productService.findProducts().get(i), cartItemToRefresh, cartItemToRefresh);
            }
        }
    }

    public void deleteCartItem(String id, HttpServletRequest request) {
        Cart cart = getCart(request);
        cart.setCartItemList(cart.deleteCartItem(id));
    }
}
