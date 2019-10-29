package com.es.phoneshop.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private ProductListPageServlet servlet = new ProductListPageServlet();

    @Before
    public void setup() throws ServletException{
        servlet.init();
        when(request.getRequestDispatcher("/WEB-INF/pages/productList.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void whenDoGetError() throws ServletException, IOException {
        servlet.doGet(request, response);
        when(request.getAttribute("products")).thenReturn(servlet.productListDao.findProducts());
        System.out.println(request.getAttribute("products"));
        verify(requestDispatcher).forward(request, response);
    }
}
