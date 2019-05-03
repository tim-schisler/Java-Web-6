<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="includes/header.html" />

<h1>Thank you for your order.</h1>
<h2>Here is the info you entered:</h2>

<span>
    <label>Email:</label>
    ${user.email}
</span><br>
<span>
    <label>First Name:</label>
    ${user.firstName}
</span><br>
<span>
    <label>Last Name:</label>
    ${user.lastName}
</span><br>

<table>
    <tr>
        <th>Cover</th>
        <th>Title</th>
        <th>Price</th>
        <th>Amount</th>
        <th>Quantity</th>
    </tr>
    <c:forEach var="i" items="${cart.items}"><tr>
        <td><img width="100" alt="Cover" src="<c:out value="${i.book.coverUrl}"/>"></td>
        <td><c:out value="${i.book.title}" /></td>
        <td><c:out value="${i.book.price}" /></td>
        <td><c:out value="${i.totalCurrencyFormat}" /></td>
        <td><c:out value="${i.quantity}" /></td>
    </tr></c:forEach>
    <tr>
        <td colspan="3">Total</td>
        <td colspan="2"><c:out value="${cart.totalCurrencyFormat}" /></td>
    </tr>
</table>

<form method="post" action="">
    <input type="hidden" name="action" value="reset">
    <input type="submit" value="Make Another Order">
</form>

<c:import url="includes/footer.jsp" />