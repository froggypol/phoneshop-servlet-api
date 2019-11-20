package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.custom.exceptions.CustomNoSuchElementException;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.SessionCartService;
import service.ProductService;
import validation.CustomValidation;
import validation.ErrorMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedList;
import java.util.Queue;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductDetailsPageServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ProductService productService;

    @Mock
    private SessionCartService cartService;

    @Mock
    private HttpSession session;

    @Mock
    private Cart cart;

    private String quantity = "5";

    private Queue<Product> recentlyViewedProds = new LinkedList<>();

    private String uri = "boo/bobo";

    private String productID = "bobo";

    private ErrorMap errorMap = new ErrorMap();

    private CustomValidation customValidation = CustomValidation.getInstance();

    private Product product = new Product("Siemens2322 SXG75",
            new BigDecimal(150), Currency.getInstance("USD"), 40,
            "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens" +
                    "/Siemens%20SXG75.jpg");

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        servlet.setProductService(productService);
        servlet.setCartService(cartService);
        recentlyViewedProds.add(product);
    }

    @Test
    public void correctWorkWhenProductDetailsPageServletDoGetTest() throws ServletException, IOException, CustomNoSuchElementException {
        when(request.getSession()).thenReturn(session);
        when(request.getRequestURI()).thenReturn(uri);
        when(servlet.getProductId(request)).thenReturn(productID);
        when(productService.getProductById(productID)).thenReturn(product);

        try {
            productService.getProductById(productID);
        } catch (CustomNoSuchElementException e) {
            fail("No such product");

            verify(request).getRequestDispatcher("/WEB-INF/pages/customError.jsp");
            verify(requestDispatcher).forward(request, response);
        }
        when(request.getRequestDispatcher("/WEB-INF/pages/productDetailsPage.jsp")).thenReturn(requestDispatcher);
        when(request.getParameter("quantity")).thenReturn(quantity);
        //doNothing().when(cartService).addProductToViewedList(product);
        doNothing().when(cartService).addToCart(productID, Integer.valueOf(quantity), request, response);
        when(cartService.getCart(request, response)).thenReturn(cart);
        //when(cart.getRecentlyViewedProducts()).thenReturn(recentlyViewedProds);

        servlet.doGet(request, response);

        verify(request).setAttribute("prod", product);
        verify(request).setAttribute("quantity", quantity);
        verify(request).getRequestDispatcher("/WEB-INF/pages/productDetailsPage.jsp");
        verify(requestDispatcher).forward(request, response);
        spy(request).setAttribute("recentlyViewedProducts", recentlyViewedProds);
    }

//    @Test
//    public void correctWorkWhenProductDetailsPageServletDoPostTest() throws ServletException, IOException, CustomNoSuchElementException {
//        when(request.getSession()).thenReturn(session);
//        when(request.getRequestURI()).thenReturn(uri);
//        when(request.getParameter("quantity")).thenReturn(quantity);
//        when(productService.getProductById(productID)).thenReturn(product);
//        when(request.getRequestURI()).thenReturn(uri);
//        customValidation.validQuantityNumberFormat(errorMap,request, response)) {
//            doNothing().when(errorMap).customException(quantity, "NaN");
//
//            verify(request).setAttribute("error", errorMap.getExceptionList(quantity));
//            verify(request).getRequestDispatcher("/WEB-INF/pages/customError.jsp");
//            verify(requestDispatcher).forward(request, response);
//
//            fail("NaN");
//        customValidation.validQuantityNumberFormat(errorMap, request, response);
//            doNothing().when(cartService).addToCart(productID, Integer.valueOf(quantity), session);
//        } else {
//            spy(response).sendRedirect(uri);
//
//            fail("Not enough products are in stock");
//        }
//
//        servlet.doPost(request, response);
//
//        spy(response).sendRedirect(uri);
//    }
}
