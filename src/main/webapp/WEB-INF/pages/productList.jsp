<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>

<jsp:useBean id="products" type="java.util.List" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <tr>
        <td></td>
    </tr>
    </p>
    <tr>
        <table class="tableProd">
            <thead>
            <tr>
                <td>Image</td>
                <td>Description
                    <form>
                        <input href="products/ajax?query=${param.query}&sortField=description&order=asc"
                               type="button" id="descriptionSelectAsc" value="asc" onclick="f1()">
                    </form>
                    <form>
                        <input type="button" id="descriptionSelectDesc" value="desc" onclick="f2()"
                               href="products/ajax?query=${param.query}&sortField=description&order=desc">
                        </button>
                    </form>
                </td>
                <td>Price
                    <form>
                        <input type="button" id="priceSelectAsc" value="asc" onclick="f3()"
                               href="products/ajax?query=${param.query}&sortField=price&order=asc">
                        </inputbutton>
                    </form>
                    <form>
                        <input type="button" id="priceSelectDesc" value="desc" onclick="f4()"
                               href="products/ajax?query=${param.query}&sortField=price&order=desc">
                        </inputbutton>
                    </form>
                </td>
            </tr>
            </thead>

            <c:forEach var="product" items="${products}">
                <tr class="prod">
                    <td>
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/
                                 ${product.imageUrl}">
                    </td>
                    <td>
                        <a href="prod/${product.id}">${product.description}</a>
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
    </tr>
</tags:master>

<script type="text/javascript">
    function f1() {
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                data: $(".product-tile").val(),
                url: '${pageContext.request.contextPath}/products/sortedList?query=${param.query}&sortField=description&order=asc',
                success: function (result) {
                    var prods = JSON.parse(result);
                    var s = '';
                    for (var i = 0; i < prods.length; i++) {
                        s += '<tr class="prod">' + '<td>';
                        s += '<img style="max-width: 64px" src="' + prods[i].imageUrl + '">';
                        s += '<td>' + prods[i].description + '</td>';
                        s += '<td>' + prods[i].price + " " + prods[i].currency + '</td>' + '</tr>';
                    }
                    $('.tableProd').html(s);
                }
            })
        })
    }

    function f2() {
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                data: $(".product-tile").val(),
                url: '${pageContext.request.contextPath}/products/sortedList?query=${param.query}&sortField=description&order=desc',
                success: function (result) {
                    var prods = JSON.parse(result);
                    var s = '';
                    for (var i = 0; i < prods.length; i++) {
                        s += '<tr class="prod">' + '<td>';
                        s += '<img style="max-width: 64px" src="' + prods[i].imageUrl + '">';
                        s += '<td>' + prods[i].description + '</td>';
                        s += '<td>' + prods[i].price + " " + prods[i].currency + '</td>' + '</tr>';
                    }
                    $('.tableProd').html(s);
                }
            })
        })
    }

    function f3() {
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                data: $(".product-tile").val(),
                url: '${pageContext.request.contextPath}/products/sortedList?query=${param.query}&sortField=price&order=asc',
                success: function (result) {
                    var prods = JSON.parse(result);
                    var s = '';
                    for (var i = 0; i < prods.length; i++) {
                        s += '<tr class="prod">' + '<td>';
                        s += '<img style="max-width: 64px" src="' + prods[i].imageUrl + '">';
                        s += '<td>' + prods[i].description + '</td>';
                        s += '<td>' + prods[i].price + " " + prods[i].currency + '</td>' + '</tr>';
                    }
                    $('.tableProd').html(s);
                }
            })
        })
    }

    function f4() {
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                data: $(".product-tile").val(),
                url: '${pageContext.request.contextPath}/products/sortedList?query=${param.query}&sortField=price&order=desc',
                success: function (result) {
                    var prods = JSON.parse(result);
                    var s = '';
                    for (var i = 0; i < prods.length; i++) {
                        s += '<tr class="prod">' + '<td>';
                        s += '<img style="max-width: 64px" src="' + prods[i].imageUrl + '">';
                        s += '<td>' + prods[i].description + '</td>';
                        s += '<td>' + prods[i].price + " " + prods[i].currency + '</td>' + '</tr>';
                    }
                    $('.tableProd').html(s);
                }
            })
        })
    }
</script>
