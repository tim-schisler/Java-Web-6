<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="includes/header.html" />


    
<h1>Book List</h1>
<table>
    <tr>
        <th>Cover</th>
        <th>Title</th>
        <th>Price</th>
        <th>&nbsp;</th>
    </tr>

    <c:forEach items="${list}" var="b">
    <tr>
        <td><img alt="Cover" height="100" src="<c:out value="${b.coverUrl}" />" /></td>
        <td><c:out value="${b.title}" /></td>
        <td><c:out value="${b.priceCurrencyFormat}" /></td>
        <td>
            <form action="" method="post">
                <input type="hidden" name="action" value="cart">
                <input type="hidden" name="title" value="<c:out value="${b.title}" />">
                <input type="submit" value="Add To Cart">
            </form>
        </td>
    </tr>
    </c:forEach>
    
</table>    

<c:import url="includes/footer.jsp" />