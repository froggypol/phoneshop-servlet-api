package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
import validation.ErrorMap;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OrderService implements OrderDao {

    final static String DATE_FORMAT = "yyyy-MM-dd";

    private static OrderService orderDaoService;

    private ProductService productService;

    private OrderService() {
        productService = ProductService.getInstance();
    }

    public static OrderService getInstance() {
        if (orderDaoService == null) {
            orderDaoService = new OrderService();
        }
        return orderDaoService;
    }

    @Override
    public Order placeOrder(Cart cart, HttpServletRequest request) throws ParseException {
        Order order = new Order(cart);
        String wayOfPayment = request.getParameter("payment");
        order.recalculate(wayOfPayment);
        order.setName(request.getParameter("firstName"));
        if (order.getName() != null) {
            order.setSurName(request.getParameter("secondName"));
            order.setAddress(request.getParameter("address"));
            order.setDate(new SimpleDateFormat(DATE_FORMAT).parse(request.getParameter("date")));
            order.setPhoneNumber(request.getParameter("phone"));
        }
        return order;
    }

    public void checkoutStock(ErrorMap errorMap, Cart cart) {
        Order order = new Order(cart);
        order.getListCartItem().stream()
                               .forEach(cartItem -> {
                                   Product checkedProduct = productService.findProducts().get(order.getListCartItem().indexOf(cartItem));
                                   if (cartItem.getQuantity() > checkedProduct.getStock()) {
                                       errorMap.customException("quantity&" + checkedProduct.getId(), "Not enough stock. Avaliable " + checkedProduct.getStock());
                                   }
                               });
    }
}
