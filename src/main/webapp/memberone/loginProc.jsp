<%@page import="tommy.web.memberone.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% StudentDAO dao = StudentDAO.getInstance(); 

String id =request.getParameter("id");
String pass =request.getParameter("pass");
int check = dao.loginCheck(id,pass);
%>

<%
if(check == 1) {//로그인 성공
	session.setAttribute("loginID", id);
    response.sendRedirect("login.jsp");
}else if(check==0){//아이디는 있는데 비밀번호 오류
%>
<script>
		alert("비밀번호가 틀렸습니다.");
		history.go(-1);
</script>
<%}else if(id.isEmpty()){//아이디를 입력안했을 경우  <- 추가 1
%>
<script>
		alert("아이디를 입력해주세요.");
		history.go(-1);
</script>

<%}else if(check==2){//아이디는 있는데 비밀번호 입력안했을 경우 <- 추가 2
%>
<script>
		alert("비밀번호를 입력해 주세요.");
		history.go(-1);
</script>
<% }else {//아이디 자체가 존재하지 않는 경우
%>
<script>
 alert("아이디가 존재하지 않습니다.");
 history.go(-1);
</script>
<%}%>