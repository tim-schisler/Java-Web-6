<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="includes/header.html" />

<h1>Sign In</h1>

<%-- Alert the user if password is wrong. --%>
<c:if test="${message != null}">
    <p><i>${message}</i></p>
</c:if>

<form method="post" action="">
    <label>Email:<input type="email" name="email" /></label><br>
    <label>Password:<input type="password" name="password" /></label><br>
    <input type="hidden" name="action" value="check_user" />
    <input type="submit" value="Sign In" />
</form>

<c:import url="includes/footer.jsp" />