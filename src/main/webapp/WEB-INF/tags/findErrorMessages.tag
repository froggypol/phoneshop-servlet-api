<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="tempErrorMap" required="true" type="java.util.HashMap" %>
<%@ attribute name="currentProduct" required="true" type="com.es.phoneshop.model.product.Product" %>
<%@ attribute name="keyProductId" required="true" type="com.es.phoneshop.model.product.Product" %>

<c:forEach var="keyProductId" items="${tempErrorMap.keySet()}">
    <c:if test="${keyProductId.equals('quantity&'.concat(currentProduct.id))}">
        <c:forEach var="errorMessage" items="${tempErrorMap.get(keyProductId)}">
            <p style="color: crimson">
                <a>${errorMessage} for ${currentProduct.description}</a>
            </p>
        </c:forEach>
    </c:if>
</c:forEach>
