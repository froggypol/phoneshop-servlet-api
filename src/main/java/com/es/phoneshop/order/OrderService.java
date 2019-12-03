package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.custom.exceptions.CustomNoSuchOrderException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
import validation.ErrorMap;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.UUID;

public class OrderService implements OrderDao {

    final static String DATE_FORMAT = "yyyy-MM-dd";

    private static OrderService orderDaoService;

    private ProductService productService;

    private CustomOrderDao orderDao;

    private OrderService() {
        productService = ProductService.getInstance();
        orderDao = CustomOrderDao.getInstance();
    }

    public static OrderService getInstance() {
        if (orderDaoService == null) {
            orderDaoService = new OrderService();
        }
        return orderDaoService;
    }

    @Override
    public void placeOrder(Order order, String wayOfPayment){
        orderDao.recalculate(wayOfPayment, order);
        orderDao.saveOrder(order);
    }

    public void saveCustomerInfo(HttpServletRequest request, Order order) throws ParseException {
        order.setName(request.getParameter("firstName"));
        if (order.getName() != null) {
            order.setSurName(request.getParameter("secondName"));
            order.setAddress(request.getParameter("address"));
            order.setDate(new SimpleDateFormat(DATE_FORMAT).parse(request.getParameter("date")));
            order.setPhoneNumber(request.getParameter("phone"));
            order.setId(UUID.randomUUID().toString());
        }
    }

    public void checkoutStock(ErrorMap errorMap, Cart cart) {
        Order order = new Order(cart);
        order.getListCartItem().stream()
                               .forEach(cartItem -> {
                                   Product checkedProduct = productService.findProducts().get(order.getListCartItem().indexOf(cartItem));
                                   if (cartItem.getQuantity() > checkedProduct.getStock()) {
                                       errorMap.customException("quantity&" + checkedProduct.getId(),
                                               "Not enough stock. Available" + checkedProduct.getStock());
                                   }
                               });
    }

    public Optional<Order> getOrderById(String id) throws CustomNoSuchOrderException {
        return orderDao.getOrderList().stream()
                                      .filter(order -> order.getId().equals(id)).findAny();
    }
}
