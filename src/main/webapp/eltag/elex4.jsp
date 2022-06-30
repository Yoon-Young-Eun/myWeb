<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%
  request.setAttribute("name", "윤영은");
%>    

<html>
<head>
<meta charset="UTF-8">
<title>EL Object</title>
</head>
<body>
요청 URI : ${pageContext.request.requestURI}<br></br>
request의 name속성 : ${requestScope.name}<br></br>
code 파라미터 : ${param.code}<br></br>

</body>
</html>
<!--  http://localhost:8080/myWeb/eltag/elex4.jsp?code=1234 치면 code값이 나옴 -->