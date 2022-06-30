<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
request.setCharacterEncoding("utf-8");  
String name=request.getParameter("name");  //fileUpload.jsp에서 넘겨받은 hidden값을 변수에 저장
String subject=request.getParameter("subject");
String filename1=request.getParameter("filename1");
String filename2=request.getParameter("filename2");
String origfilename1=request.getParameter("origfilename1");
String origfilename2=request.getParameter("origfilename2");
%>
<html>
<head>
<title>파일 업로드 확인 및 다운로드</title></head>
<body>
<!-- 받은 변수값을 아래와 같이 출력 -->
올린 사람 : <%=name %><br>
제목 : <%=subject %><br>
파일명1 : <a href="/myWeb/upload/<%=filename1 %>"><%=origfilename1 %></a><br>   <!-- 파일명 클릭시 경로를  href처리하여 열람케함 -->
파일명2 : <a href="/myWeb/upload/<%=filename2 %>"><%=origfilename2 %></a><p>
       
</body>
</html>