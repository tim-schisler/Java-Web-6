<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/includes/header.html" />

<h1>Welcome to the Book Store.</h1>
<p>Please click the button to view the book list.</p>
<form method="post" action="cart">
    <input type="hidden" name="action" value="shop" />
    <input type="submit" value="View Book List" />
</form>

<c:import url="includes/footer.jsp" />