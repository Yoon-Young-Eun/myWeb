<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.MultipartRequest, 
               com.oreilly.servlet.multipart.DefaultFileRenamePolicy, 
               java.util.*, 
               java.io.*"
%>
<%
String realFolder=""; //MultipartRequest의 파일경로 요소
String saveFolder="upload"; //저장경로
String encType="utf-8";
int maxSize=10*1024*1024; //10M
ServletContext context = getServletContext(); // ServletContext(하나의 웹애플리케이션에 대한 정보를 가진 객체)
realFolder=context.getRealPath(saveFolder); //파일 업로드 경로
ArrayList saveFiles = new ArrayList(); //다운로드시 파일이름
ArrayList origFiles = new ArrayList(); //업로드시 파일이름
String paramUser = "";
String paramTitle="";
String paramAbstract="";
try
{ 
	
	 MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
	 paramUser=multi.getParameter("txtUser");  // 받은 요소값을 변수에 저장
	 paramTitle=multi.getParameter("txtTitle");
	 paramAbstract=multi.getParameter("txtAbstract");
	 Enumeration files = multi.getFileNames();  //MultipartRequest부터 얻은 데이터로 file 이름 저장
	 while(files.hasMoreElements()){ //다음값이 있으면 참
		 String name=(String)files.nextElement(); //file이름을 name에 저장
		 saveFiles.add(multi.getFilesystemName(name)); //다운받아 저장된 file이름을 saveFiles 배열에 담는다.
		 origFiles.add(multi.getOriginalFileName(name)); //업로드 당시 file 이름을 origFiles 배열에 담는다.
	 }
%>
<html>
<head>
<title>File Info Page</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="75%" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#660000" bgcolor="#FFFF99">
<tr>
   <td width="10%" bgcolor="#FFCCOO">
      <div align="right"><strong>user</strong></div></td>
   <td width="30%"><%=paramUser %></td>
   <td width="10%" bgcolor="#FFCC00">
      <div align="right"><strong>title</strong></div></td>      
   <td width="30%"><%=paramTitle%></td>
</tr>
<tr>
   <td width="10%" bgcolor="#FFCC00">
   <div align="right"><strong>Abstract</strong></div></td>
   <td width="50%" colspan="3">
     <textarea cols="50" rows="5" disabled><%=paramAbstract %></textarea>
   </td>
</tr>
<tr><td colspan="4" bgcolor="#ffffff">&nbsp;</td></tr>
<tr>
   <td colspan="4"><strong>업로드된 파일들입니다. </strong></td>
</tr>
<%for(int i=0;i<saveFiles.size();i++){ %> 
<tr bgcolor="#FFCCOO">
    <td colspan="4">
    <!-- 업로드된 파일의 이름을 누르면 /myWeb/경로(folder)/파일이름 을 열람할 수있다. -->
    <a href="<%="/myWeb/" %>" + saveFolder+"/"+saveFiles.get(i)%>"><strong><%=origFiles.get(i) %></strong></a> 
    </td>
</tr>
<%} %>
</table>
</body>
</html>
<% 
}catch(IOException ioe){
    System.out.println(ioe);
    ioe.printStackTrace();
}catch(Exception ex){
    System.out.println(ex);
 }%>
