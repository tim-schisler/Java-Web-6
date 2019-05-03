<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="includes/header.html" />

<h1>Your Cart</h1>
        
<table>
    <tr>
        <th>Cover</th>
        <th>Title</th>
        <th>Price</th>
        <th>Amount</th>
        <th>Quantity</th>
        <th>&nbsp;</th>
    </tr>

<c:forEach var="i" items="${cart.items}">
    <tr>
        <td><img height="100" alt="Cover" src="<c:out value="${i.book.coverUrl}"/>"></td>
        <td><c:out value="${i.book.title}"/></td>
        <td><c:out value="${i.book.priceCurrencyFormat}"/></td>
        <td><c:out value="${i.totalCurrencyFormat}"/></td>
        <td><form action="" method="post">
            <input type="hidden" name="title" 
                   value="<c:out value='${i.book.title}'/>">
            <input type=text name="quantity" 
                   value="<c:out value='${i.quantity}'/>" id="quantity">
            <input type="submit" value="Update">
        </form></td>
        <td><form action="" method="post">
            <input type="hidden" name="title" 
                   value="<c:out value='${i.book.title}'/>">
            <input type="hidden" name="quantity" value="0">
            <input type="submit" value="Remove Item">
        </form></td>
    </tr>
</c:forEach>

</table>

<p><b>To change the quantity</b>, enter the new quantity and click on the Update button.</p>

<form action="" method="post">
  <input type="hidden" name="action" value="shop">
  <input type="submit" value="Continue Shopping">
</form>

<form action="" method="post">
  <input type="hidden" name="action" value="login">
  <input type="submit" value="Checkout">
</form>

<c:import url="includes/footer.jsp" />