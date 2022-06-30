<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8"/>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL fmt 예제 - requestEncoding</title>
</head>
<body>
파라메터 : <c:out value="${param.id }"/>
<form method="post" action ="fmtex2.jsp">
    <input type ="text" name="id">
    <input type ="submit" value="확인">
</form>

</body>
</html>