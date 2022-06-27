<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ page errorPage = "/error/error.jsp" %>   --%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파라미터 출력</title>
</head>
<body>
name 파라미터 값;
<%= request.getParameter("name").toUpperCase() %>

</body>
</html>

<!--  http://localhost:8080/myWeb/directive/errinvoke.jsp?name=값   을 넣어주면 에어안생김 -->