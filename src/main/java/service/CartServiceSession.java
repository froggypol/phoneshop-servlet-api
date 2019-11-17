package service;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartServiceSession implements CartService {

    private Cart cart;

    private static CartServiceSession cartService;

    private List<CartItem> cartItemList;

    private ProductService productService;

    private CartItem cartItem;

    private CartServiceSession() {
        cartItemList = new ArrayList<>();
        productService = ProductService.getInstance();
    }

    public static CartServiceSession getInstance() {
        if (cartService == null) {
            cartService = new CartServiceSession();
        }
        return cartService;
    }

    @Override
    public Cart getCart(HttpSession session) {
        cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    public int countQuantity() {
        int resultQuantity = 0;
        for (CartItem cartItem : cartItemList) {
            resultQuantity = resultQuantity + cartItem.getQuantity();
        }
        return resultQuantity;
    }

    @Override
    public BigDecimal countCost() {
        BigDecimal resultCost = BigDecimal.ZERO;
        for (CartItem cartItem : cartItemList) {
            resultCost = resultCost.add(cartItem.getProductItem().getPrice());
        }
        return resultCost;
    }

    @Override
    public void addToCart(String id, int quantity, HttpSession session) throws OutOfStockException {
        cart = getCart(session);
        Product productToAdd = productService.getProductById(id);
        cartItem = new CartItem(quantity, productToAdd);
        cartItemList = cart.getListCartItem();
        if (!cart.getListCartItem().contains(cartItem)) {
            productToAdd.setOrdered(quantity);
            cart.getListCartItem().add(cartItem);
            return;
        } else {
            int indexAddedItem = cart.getListCartItem().indexOf(cartItem);
            Product addedProduct = cart.getListCartItem().get(indexAddedItem).getProductItem();
            cartItemList.add(cartItem);
            addedProduct.setOrdered(quantity + addedProduct.getOrdered());
            recalculate();
        }
    }

    public void recalculate() {
        int resultQuantity = countQuantity();
        BigDecimal resultCost = countCost();
        cart.setTotalQuantity(resultQuantity);
        cart.setTotalCost(resultCost);
    }

    public void addProductToViewedList(Product product) {
        if (cart.getRecentlyViewedProducts().contains(product))
            return;
        if (cart.getRecentlyViewedProducts().size() == 3) {
            cart.getRecentlyViewedProducts().remove();
        }
        cart.getRecentlyViewedProducts().add(product);
    }

    public void setCartList(List<CartItem> list) {
        cartItemList = list;
    }
}
