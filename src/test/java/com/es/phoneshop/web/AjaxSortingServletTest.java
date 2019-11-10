package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class AjaxSortingServletTest {

    @Mock
    private  HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ProductService service;

    @Mock
    private PrintWriter out;

    private String fieldToSort = "field";

    private String orderToSort = "order";

    private String productname = "pName";

    private List<Product> result = Collections.singletonList(new Product());

    private AjaxSortingServlet servlet = new AjaxSortingServlet();

    private Gson gson = new Gson();

    @Before
    public void setup() {
        servlet.setService(service);
    }

    @Test
    public void correctWorkWhenAjaxSortingServletDoGetTest() throws IOException {
        when(request.getParameter("sortField")).thenReturn(fieldToSort);
        when(request.getParameter("order")).thenReturn(orderToSort);
        when(request.getParameter("query")).thenReturn(productname);
        when(service.findProducts(productname, fieldToSort, orderToSort)).thenReturn(result);
        when(response.getWriter()).thenReturn(out);
        doNothing().when(out).print(gson.toJson(result));

        servlet.doGet(request, response);

        verify(out).print(gson.toJson(result));
        verify(out).close();
        verify(out).flush();
    }
}
