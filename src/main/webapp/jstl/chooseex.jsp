<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
  <c:choose>
     <c:when test="{조건식1}"
          조건식1이 참인 경우 수행된다.
     </c:when>
     <c:when test="{조건식2}"
          조건식2가 참인 경우 수행된다.
     </c:when>
     <c:oherwise>
          조건식1과 조건식2를 모두 만족하지 못한 경우 수행된다.
     </c:therwise>
  </c:choose>
 --%>
 
 <%
   int score = 85;
   request.setAttribute("score", score);
 %>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL Example</title>
</head>
<body>
<c:choose>
  <c:when test="${score ge 90 }"> 당신의 성적은 A입니다.</c:when>
  <c:when test="${score ge 80 }"> 당신의 성적은 B입니다.</c:when>
  <c:when test="${score ge 70 }"> 당신의 성적은 C입니다.</c:when>
  <c:when test="${score ge 60 }"> 당신의 성적은 D입니다.</c:when>
  <c:otherwise> 당신의 성적은 F입니다.</c:otherwise>
</c:choose>
</body>
</html>