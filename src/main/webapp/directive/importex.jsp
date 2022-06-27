<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import='java.util.Calendar' %>   <!-- import 추가 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Calendar 클래스 사용</title></head>
<body>
 <%
   // java.util.Calendar cal = java.util.Calendar.getInstance();
    Calendar cal = Calendar.getInstance();
 %>
 
 오늘은
    <%= cal.get(Calendar.YEAR) %> 년  <!--  cal.get(java.util.Calendar.YEAR) 에서 import 문법 제거 -->
    <%= cal.get(Calendar.MONTH)+1 %> 월 <!-- 1일이 0부터 시작해서 +1을 해줘야 한다. -->
    <%= cal.get(Calendar.DATE) %>일
입니다.

</body>
</html>