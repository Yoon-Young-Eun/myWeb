<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.net.URLEncoder" %>
<%
  Cookie cookie = new Cookie("name", URLEncoder.encode("윤영은","utf-8"));
   //쿠키는 한글을 표현할 수 없기에 (아스키코드,숫자,알파벳 가능) 한글 쿠기 이름은 utf로 암호화해서 보낸다.(URLEncoder.encode())
   //받을 때는 암호화된 세션이름을 URLDecode.decode() 하여 한글로 변환하여 출력 가능하다. 
  response.addCookie(cookie); //쿠기의 객체를 웹 브라우저로 보낸다.
%>

<html>
<head><title>쿠기생성</title></head>
<body>
<%= cookie.getName() %>쿠키의 값 ="<%=cookie.getValue() %>

</body>
</html>