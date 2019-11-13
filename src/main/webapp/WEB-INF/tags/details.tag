<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="productName" required="true" %>
<%@ attribute name="productId" required="true" %>

<a href="products/?productId=${productId}">${productName}</a>
