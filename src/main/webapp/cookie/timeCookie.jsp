<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  Cookie cookie = new Cookie("hour", "1time");
  cookie.setMaxAge(60); //60초 (1분)
  //setMaxAge() 쿠키의 유효시간을 초단위로 지정한다.  
  response.addCookie(cookie);
%>

<html>
<head><title>쿠키유효시간 설정</title></head>
<body>
유효시간이 1시간인 hour 쿠기 생성.
</body>
</html>