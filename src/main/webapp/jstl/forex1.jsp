<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core"  %>
<%--
c:foreach items="${리스트가 받아올 배열이름}"
           var="for문 내부에서 사용할 변수"
           varStatus="상태용 변수">
 
	// 반복해서 표시할 내용 혹은 반복할 구문
 
</c:foreach>
 --%>
<%
   String[] movieList ={"브레이킹배드", "콘스탄트 카드너", "노킹온 해븐스 도어"};
   request.setAttribute("movieList", movieList);
%>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL Example</title>
</head>
<body>
인기 미국 드라마 :
<ul>
    <c:forEach var="movie" items="${movieList}">
       <li>${movie}</li>
    </c:forEach>
</ul>
</body>
</html>