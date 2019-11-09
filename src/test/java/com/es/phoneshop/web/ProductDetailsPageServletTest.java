package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ProductService service;

    private String productID = "PID";

    private Product product = new Product("Siemens2322 SXG75",
            new BigDecimal(150), Currency.getInstance("USD"), 40,
            "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens" +
                    "/Siemens%20SXG75.jpg");

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        servlet.setService(service);
    }

    @Test
    public void whenDoGetError() throws ServletException, IOException {
        when(request.getParameter("productId")).thenReturn(productID);
        when(service.getProduct(productID)).thenReturn(product);
        when(request.getRequestDispatcher("/WEB-INF/pages/productDetailsPage.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("products", product);
        verify(request).getRequestDispatcher("/WEB-INF/pages/productDetailsPage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}
