<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="tommy.mvcMem.model.StudentDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta http-equiv="Refresh" content="1;url=member.mdo?cmd=login">
<title>delete process</title>
</head>
<body>
<c:set var="result" value="${result}"/>
<center>
 <c:if test="${result eq 0}"><script type='text/javascript'>alert('비밀 번호가 틀렸습니다.'); history.go(-1);
 </script>
 </c:if>
<font size="5" face="바탕체">회원정보가 삭제 되었습니다.<br/>
안녕히 가세요.<br/>1초후에 login page로 이동합니다.</font>
</center>
</body>
</html>