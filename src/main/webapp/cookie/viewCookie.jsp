<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.net.URLDecoder" %>
<html>
<head>
<meta charset="UTF-8">
<title>쿠키목록</title>
</head>
<body>
쿠키 목록<br>
<%
  Cookie[] cookies = request.getCookies(); //쿠키 값 읽어 오기 (다양한 정보가 들어갈 수 있으므로, 배열로 받아야 한다.)
  if(cookies != null && cookies.length > 0){
	  for(int i = 0; i < cookies.length ; i++){
		  %>
		  <%=cookies[i].getName() %> = 
		  <%=URLDecoder.decode(cookies[i].getValue(), "utf-8") %><br> <!--URLDecoder.decode 인코딩된 값을 한글로 번역 -->
		  <%
	  }
  }else{
%>
쿠키가 존재하지 않습니다.
<%
}%>
</body>
</html>