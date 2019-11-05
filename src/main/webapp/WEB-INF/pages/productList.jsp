<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.List" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    </p>
    <p>
        <tr>
            <td>${product.name}</td>
        </tr>
    </p>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td name="description">Description
                <tags:sort sortField="description" order="asc" query="${param.query}"></tags:sort>
                <tags:sort sortField="description" order="desc" query="${param.query}"></tags:sort>
            </td>
            <td name="price">Price
                <tags:sort sortField="price" order="asc" query="${param.query}"></tags:sort>
                <tags:sort sortField="price" order="desc" query="${param.query}"></tags:sort>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <tags:details productName="${product.description}" productId="${product.id}"></tags:details>
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                    <tags:menu productDescription="${product.description}" date="${product.priceHistory.date}"
                               priceArchieve="${product.priceHistory.priceArchieve}">
                    </tags:menu>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>
