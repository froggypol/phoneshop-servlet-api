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
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class ProductListPageServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ProductService service;

    private String fieldToSort = "sss";

    private String orderToSort = "oo";

    private String productname = "pppp";

    private List<Product> result = Collections.singletonList(new Product());

    private ProductListPageServlet servlet = new ProductListPageServlet();

    @Before
    public void setup() {
        servlet.setService(service);
    }

    @Test
    public void correctWorkWhenProductListPageServletDoGetTest() throws ServletException, IOException {
       when(request.getParameter("sortField")).thenReturn(fieldToSort);
       when(request.getParameter("order")).thenReturn(orderToSort);
       when(request.getParameter("query")).thenReturn(productname);
       when(service.findProducts(productname, fieldToSort, orderToSort)).thenReturn(result);
       when(request.getRequestDispatcher("/WEB-INF/pages/productList.jsp")).thenReturn(requestDispatcher);

       servlet.doGet(request, response);

       verify(request).setAttribute("products", result);
       verify(request).getRequestDispatcher("/WEB-INF/pages/productList.jsp");
       verify(requestDispatcher).forward(request, response);
    }
}
