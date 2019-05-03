<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="includes/header.html" />

<h1>New User Registration</h1>
<form method="post" action="">
    <label>Email:<input type="email" value="${user.email}" /></label><br>
    <label>Password:<input type="password" value="${user.password}" /></label><br>
    <label>First Name:<input type="text" name="first" /></label><br>
    <label>Last Name:<input type="text" name="last" /></label><br>
    <input type="hidden" name="action" value="register" />
    <input type="submit" value="Join Now" />
</form>

<c:import url="includes/footer.jsp" />