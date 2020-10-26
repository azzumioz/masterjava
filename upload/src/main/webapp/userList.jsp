<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Result</title>
</head>
<body>
<c:set var="users" value="${requestScope.users}"/>
<table>
  <tr>
    <th>Name</th>
    <th>Email</th>
    <th>Flag</th>
  </tr>
  <c:forEach items="${users}" var="user">
    <tr>
      <td>${user.name}</td>
      <td>${user.email}</td>
      <td>${user.flag}</td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
