<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="prod" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product List Details Page">
    <p>
        <tr>
            <a href="/phoneshop_servlet_api_war_exploded/products">Back To The Main Page</a>
        </tr>
    </p>
    <table>
        </p>
        <p>
            <form method="post">
                <input id="bttAdd" name="quantity" value="${param.query}">
                <input type="hidden" name="productId" value="${prod.id}">
                <button class="btn btn-info">Add</button>
                <c:choose>
                <c:when test="${not empty errorMap}">
        <p>
            <a style="color: crimson">Adding products failed:c</a>
        </p>
        </c:when>
        <c:when test="${not empty param.message}">
            <p>
                <a style="color: seagreen">Updating ${param.message} c:</a>
            </p>
        </c:when>
        </c:choose>
        </form>
        </p>
        <thead>
        <tr>
            <td>Image</td>
            <td name="description">Description
            </td>
            <td name="price">Price
            </td>
        </tr>
        </thead>
        <tr>
            <td>
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${prod.imageUrl}">
            </td>
            <td>${prod.description}</td>
            <td class="price">
                <fmt:formatNumber value="${prod.price}" type="currency"
                                  currencySymbol="${prod.currency.symbol}"/>
            </td>
        </tr>
    </table>

    <br>
    <form method="post">
        <button>Leave comment</button>
    <table>
        <form method="post">
            <tr>
                <td>YourName</td>
                <td>Rate for product</td>
                <td>Leave Comment</td>
            </tr>
            <td>
                <label>
                    <input type="text" name="customerName" required>
                </label>

            </td>
            <td>Format: only numbers
                <label>
                    <input type="text" name="rate" pattern="[0-9]" required>
                </label>
            </td>
            <td>
                <label>
                    <input type="text" name="comment" required>
                </label>
            </td>
        </form>
    </table>
    <br>
</form>
    <br>
    <c:forEach var="commentValue" items="${comments}">
        <div>
            <tr>
                <td>YourName</td>
                <td>Rate for product</td>
                <td>Leave Comment</td>
            </tr>
            <td>
                    <input type="text" value="${commentValue.customerName}" readonly>
            </td>
            <td>
                    <input type="text" value="${commentValue.rate}" readonly >
            </td>
            <td>
                    <input type="text" name="comment" value="${commentValue.comment}" readonly >
            </td>
        </div>
    </c:forEach>
    </p>

    <p style="text-decoration: indigo;">Recently viewed products
    <c:forEach var="product" items="${recentlyViewedProducts}">
        <div style="display: inline-block; border: 1px solid lightskyblue; width: 10%">
            <td>
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/
                                 ${product.imageUrl}">
            </td>
        <p>
            <a href="prod/${product.id}">${product.description}</a>
        </p>
        <p><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></p>
        </div>
    </c:forEach>
    </p>
</tags:master>
