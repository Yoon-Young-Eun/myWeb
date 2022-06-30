<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FileUpload Form</title>
</head>
<body>
<center>
<form action="fileUpload.jsp" method="post"  enctype="multipart/form-data">
<!-- enctype(전송되는 데이터 형식) ="multipart/form-dada" 지정하지 않은 데이터를 보내는데 get과 post 방식은 용량이 제한되어 
     있기 때문으로, 속성을 지정함으로 데이터도 파일 형태로 넘어가며 큰 용량의 데이터도 전송 할 수 있게 해주기 위함니다.
     이제 폼태그가 서버쪽으로 전송되면, 서버는 넘어온 데이터를 받아서 파일로 만드는 작업을 하면 된다. 이 업로드 모듈을
     직접 구현해도 되지만 일반적으로 많이 배포되어 있는 모듈을 사용한다. -->
<table border=1>
 <tr> 
      <td colspan =2 align = center><h3>파일 업로드 폼</h3></td>
 </tr>

 <tr>
      <td>올린 사람 : </td><td><input type="text" name="name"></td>
 </tr>
 
 <tr>
      <td>제목 : </td><td><input type="text" name="subject"></td>
 </tr>

 <tr>
      <td>파일명1 : </td><td><input type="file" name="fileName1"></td>
 </tr>
 
 <tr>
      <td>파일명2 : </td><td><input type="file" name="fileName2"></td>
 </tr>
 
  <tr>
      <td colspan=2 align=center><input type="submit" value="전송"></td>
 </tr>
 
</table>
</form>
</center>
</body>
</html>