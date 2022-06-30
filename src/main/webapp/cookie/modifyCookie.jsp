<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.net.URLEncoder" %>
<%
  Cookie[] cookies = request.getCookies();
  if(cookies != null && cookies.length > 0){
	  for(int i=0 ; i<cookies.length; i++){
		  if(cookies[i].getName().equals("name")){  //키와 같이 값이 같다면, 
			  cookies[i].setValue(URLEncoder.encode("자바와 JSP", "utf-8")); //해당 데이터를 setValue를 통해 변경해준다.
			  response.addCookie(cookies[i]);//쿠키를 브라우저에 보낸다.
		  }
	  }
  }
%>
<html>
<head><title>값 변경</title></head>
<body>
name 쿠기의 값을 변경합니다.
</body>
</html>