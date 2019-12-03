<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <head>
        <meta charset="UTF-8">
        <title>Not Found Such Product</title>
    </head>
    <body>
    <a>Your product not found!</a>
    <p>
        <a href="${pageContext.servletContext.contextPath}/products">Back To The Main Page</a>
    </p>
    </body>
</tags:master>
