<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="productDescription" required="true" %>
<%@ attribute name="date" required="true" %>
<%@ attribute name="priceArchieve" required="true" %>

<style>
    .sub-menu {
        height: 0;
        overflow: hidden;
        opacity: 0;
        transition: all 0.5s ease-in;
    }
</style>

<html>
<head>
    <title>Title</title>
</head>
<body>
<ul class="menu">
    <li class="menu-item">
        <a>Tap here to see history</a>
        <ul class="sub-menu">
            <li>
                <a>${productDescription}</a>
            </li>
            <li>
                <a>${date}</a>
            </li>
            <li>
                <c:forEach var="oldPrice" items="${priceArchieve}">
            <li>
                <a>${oldPrice}</a>
            </li>
            </c:forEach>
            </li>
        </ul>
    </li>
</ul>
<script>
    var element = document.getElementsByClassName('menu-item');
    for (var i = 0; i < element.length; i++) {
        element[i].addEventListener("mouseenter", showSub, false);
        element[i].addEventListener("mouseleave", hideSub, false);
    }

    function showSub(e) {
        if (this.children.length > 1) {
            this.children[1].style.height = "auto";
            this.children[1].style.overflow = "visible";
            this.children[1].style.opacity = "1";
        } else {
            return false;
        }
    }

    function hideSub(e) {
        if (this.children.length > 1) {
            this.children[1].style.height = "0px";
            this.children[1].style.overflow = "hidden";
            this.children[1].style.opacity = "0";
        } else {
            return false;
        }
    }
</script>
</body>
</html>


