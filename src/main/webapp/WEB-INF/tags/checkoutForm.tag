<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="field" required="true" %>
<%@ attribute name="errorMap" required="true" type="java.util.HashMap" %>

<c:choose>
    <c:when test="${not empty errorMap}">
        <a style="color: crimson">${errorMap.get(field)}</a>
    </c:when>
</c:choose>
