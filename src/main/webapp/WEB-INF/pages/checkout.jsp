<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.order.Order" scope="request"/>
<tags:master pageTitle="Checkout Page">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <p>
        <tr>
            <a href="/phoneshop_servlet_api_war_exploded/products">Back To The Main Page</a>
        </tr>
    </p>

    <table class="tableProd">
        <td>Image</td>
        <td>Description</td>
        <td>Price</td>
        <td>Quantity</td>
        <c:forEach var="cartItem" items="${order.listCartItem}">
            <tr class="prod">
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/
                                 ${cartItem.productItem.imageUrl}">
                </td>
                <td>
                    <a>${cartItem.productItem.description}</a>
                </td>
                <td class="price">
                    <fmt:formatNumber value="${cartItem.productItem.price}" type="currency"
                                      currencySymbol="${cartItem.productItem.currency.symbol}"/>
                    <tags:menu productDescription="${cartItem.productItem.description}"
                               date="${cartItem.productItem.priceHistory.date}"
                               priceArchieve="${cartItem.productItem.priceHistory.priceArchieve}">
                    </tags:menu>
                </td>
                <td>
                    <label>
                            ${cartItem.quantity}
                    </label>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <div>
        <label>
            <input value="Total Cost : ${order.subTotal}" readonly>
        </label>
        <label>
            <input type="hidden" value="${order.id}">
        </label>
    </div>
    <%--<form method="post" action="http://localhost:8080/phoneshop_servlet_api_war_exploded/checkout/overview?id=${order.id}">--%>
    <form method="post">
        <table>
            <td>Name</td>
            <td>Surname</td>
            <td>Date</td>
            <td>Address</td>
            <td>Phone: please, be careful : 80xx-xxx-xx-xx</td>
            <td>Payment</td>
            <tr>
                <td>
                    <tags:checkoutForm field="firstName" errorMap="${errorMap}"/>
                    <input name="firstName" value="${param.firstName}" required>
                </td>
                <td>
                    <tags:checkoutForm field="secondName" errorMap="${errorMap}"/>
                    <input name="secondName" value="${param.secondName}" required>
                </td>
                <td>
                    <tags:checkoutForm field="date" errorMap="${errorMap}"/>
                    <input name="date" type="date" value="${param.date}" required>
                </td>
                <td>
                    <tags:checkoutForm field="address" errorMap="${errorMap}"/>
                    <input name="address" value="${param.address}" required>
                </td>
                <td>
                    <tags:checkoutForm field="phone" errorMap="${errorMap}"/>
                    <input name="phone" type="tel"
                           pattern="80[0-9]{2}-[0-9]{3}-[0-9]{2}-[0-9]{2}"
                           value="${param.phone}" required>
                </td>
                <td>
                    <form method="post">
                        <p>
                            <label>Choose way of payment:</label>
                            <select name="payment">
                                <option value="creditCard" name="card">By card 5$</option>
                                <option value="cash" name="cash">Cash 15$</option>
                            </select>
                        </p>
                    </form>
                </td>
            </tr>
        </table>
        <br>
        <button>Place Order</button>
    </form>
</tags:master>
