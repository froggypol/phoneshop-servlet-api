<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.order.Order" scope="request"/>
<tags:master pageTitle="Checkout Page">
    <p>
        <tr>
            <a href="/phoneshop_servlet_api_war_exploded/products">Back To The Main Page</a>
        </tr>
    </p>

    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
        <table class="tableProd">
            <td>Image</td>
            <td>Description</td>
            <td>Price</td>
            <td>Quantity</td>
            <div>
                <label>
                <input value="Total Cost : ${order.subTotal}" readonly>
                </label>
                <label>
                <input value="Delivery Cost : ${order.deliveryCost}" readonly>
                </label>
            </div>
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
                            <input name="quantity" value="${cartItem.quantity}" readonly>
                        </label>
                    </td>
                </tr>
            </c:forEach>
        </table>
    Congrats!
   <p>Dear ${order.surName} ${order.name}</p>
    <p>Your order will be prepared to ${order.date}<p>
</tags:master>
