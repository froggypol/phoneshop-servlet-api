<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product List Details Page">
    <p>
        <tr>
            <a href="/phoneshop_servlet_api_war_exploded/products">Back To The Main Page</a>
        </tr>
    </p>
    <table>
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
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${products.imageUrl}">
                </td>
                <td>${products.description}</td>
                <td class="price">
                    <fmt:formatNumber value="${products.price}" type="currency"
                                      currencySymbol="${products.currency.symbol}"/>
                </td>
            </tr>
    </table>
</tags:master>
