<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.cart.Cart" scope="session"/>
<tags:master pageTitle="Cart Page">
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
            <td>Action</td>
            <td>Message</td>
            <c:forEach var="cartItem" items="${cart.listCartItem}">
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
                        <input name="quantity" value="${cartItem.quantity}">
                    </label>

                </td>
                <td>
                    <input style="visibility: hidden" name="productId" value="${cartItem.productItem.id}">
                    <button formmethod="post" formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem">Delete</button>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty errorMap[cartItem.productItem]}">
                            <p>
                                <a style="color: crimson">Adding failed:c</a>
                            <p style="color: crimson">
                                <c:forEach var="productError" items="${errorMap[cartItem.productItem]}">
                                    <a>${productError} for ${cartItem.productItem.description}</a>
                                </c:forEach>
                            </p>
                            </p>
                        </c:when>
                        <c:when test="${not empty param.message}">
                            <p style="color: forestgreen">
                                <a>Added successfully!</a>
                            </p>
                        </c:when>
                    </c:choose>
                </td>
                </tr>
            </c:forEach>
        </table>
        <p>
            <button>Update</button>
        </p>
    </form>

    <p style="text-decoration: indigo;">Recently viewed products
    <c:forEach var="product" items="${recentlyViewedProducts}">
        <div style="display: inline-block; border: 1px solid lightskyblue; width: 10%">
            <td>
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/
                                 ${product.imageUrl}">
            </td>
        <p>${product.description}</p>
        <p><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></p>
        </div>
    </c:forEach>
    </p>
</tags:master>
