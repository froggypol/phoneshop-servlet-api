<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Welcome user">
    <br>
    <p>Welcome, dear!</p>
    <form>
        <button type="submit" formaction="${pageContext.servletContext.contextPath}/products">To next step</button>
    </form>
</tags:master>
