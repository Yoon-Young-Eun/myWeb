<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page buffer="1kb" autoFlush="true" %> 
<!-- autoFlush를 false로 하게 되면 (HTTP 상태 500 : JSP 버퍼 오버플로우 에러 발생)-->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>autoFlush 속성값 false 예제</title>
</head>
<body>
<!-- 이 부분에서 4KB이상의 데이터가 발생 -->
<% for(int i =0; i<1000; i++) {  %>
 1234
 <% } %>

</body>
</html>