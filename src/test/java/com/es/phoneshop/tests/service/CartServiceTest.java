package com.es.phoneshop.tests.service;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import service.CartServiceSession;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.Silent.class)

public class CartServiceTest {

    private CartServiceSession cartServiceTest;

    private List<CartItem> cartItemList = new ArrayList<>();

    private Cart cart;

    private Product product;

    @Mock
    private HttpSession session = Mockito.mock(HttpSession.class);

    @Before
    public void setup() {
        cart = new Cart();
        product = new Product("Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        cartServiceTest = CartServiceSession.getInstance();
        cartItemList.add(new CartItem((2), new Product("Siemens SXG75", new BigDecimal(150),
                Currency.getInstance("USD"), 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master" +
                        "/manufacturer/Siemens/Siemens%20SXG75.jpg")));
        cartServiceTest.setCartList(cartItemList);
    }

    @Test
    public void gotNotNullCartWhenCartServiceTestGetCart() {
        Mockito.when(session.getAttribute("cart")).thenReturn(cart);
        Cart gotCart = cartServiceTest.getCart(session);
        assertTrue(gotCart != null);
    }

}
