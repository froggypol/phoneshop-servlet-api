package service;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartServiceSession implements CartService {

    private Cart cart;

    private static CartServiceSession cartService;

    private List<CartItem> cartItemList;

    private ProductService productService;

    private CartItem cartItem;

    private SessionService sessionService;

    private CartServiceSession() {
        cartItemList = new ArrayList<>();
        productService = ProductService.getInstance();
        sessionService = SessionService.getInstance();
    }

    public static CartServiceSession getInstance() {
        if (cartService == null) {
            cartService = new CartServiceSession();
        }
        return cartService;
    }

    @Override
    public Cart getCart(HttpServletRequest request, HttpServletResponse response) {
        cart = sessionService.getCart(request, response);
        if (cart == null) {
            cart = new Cart();
        }
        sessionService.setCart(cart, request, response);
        return cart;
    }

    @Override
    public int countQuantity() {
        return cart.getListCartItem().stream().mapToInt(CartItem::getQuantity).sum();
    }

    @Override
    public BigDecimal countCost() {
        return cart.getListCartItem().stream()
                           .map(CartItem::getProductItem)
                           .map(Product::getPrice)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void addToCart(String id, int quantity, HttpServletRequest request, HttpServletResponse response) throws OutOfStockException {
        cart = sessionService.getCart(request, response);
        Product productToAdd = productService.getProductById(id);
        cartItem = new CartItem(quantity, productToAdd);
        cartItemList = cart.getListCartItem();
        if(quantity > productToAdd.getStock() || productToAdd.getStock() == 0) {
            throw new OutOfStockException();
        }
        if (!cartItemList.contains(cartItem)) {
            cartItem.setQuantity(quantity);
            cart.getListCartItem().add(cartItem);
            return;
        } else if (cartItemList.contains(cartItem)){
            CartItem addedItem = cartItemList.get(cartItemList.indexOf(cartItem));
            if(quantity <= productToAdd.getStock() - addedItem.getQuantity()) {
                int mew = quantity + addedItem.getQuantity();
                addedItem.setQuantity(mew);
                cartItemList.set(cartItemList.indexOf(cartItem), addedItem);// refresh
                recalculate();
                cart.setCartItemList(cartItemList);
            } else {
                throw new OutOfStockException();
            }
        }
    }

    public void recalculate() {
        int resultQuantity = countQuantity();
        BigDecimal resultCost = countCost();
        cart.setTotalQuantity(resultQuantity);
        cart.setTotalCost(resultCost);
    }

    public void setCartList(List<CartItem> list) {
        cart.setCartItemList(list);
    }
}
