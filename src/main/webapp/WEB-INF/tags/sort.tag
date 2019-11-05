<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" %>
<%@ attribute name="query" required="true" %>
<%@ attribute name="sortField" required="true" %>

<a href="products?query=${param.query}&sortField=${sortField}&order=${order}">${order}</a>
