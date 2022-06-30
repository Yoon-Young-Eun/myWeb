<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import ="java.util.*" %>
<% 
/* <form method="post"> 의 형태로 전송한 폼에 담겨진 파라미터들은 
    request 객체를 통해 이름에 해당되는 값을 얻어낼 수 있다 .

    하지만 enctype="multipart/form-data"로 지정한 폼은 request객체로 파라미터의 값을 얻어낼 수 없다.
    때문에 enctype= "multipart/form-data"로 전송한 폼의 파라미터들에 대한 값을 얻어내기 위해,
    <input type="file" 로 지정된 파일을 서버상의 한 폴더에 업로드 하기 위해 특별한 컴포넌트가 필요하다.
    → http://servlets.com 에서 cos-22.05.zip 받아 jar파일 lib폴더에 저장

MultipartRequest(javax.servlet.http.HttpServletRequest request,        <- MultipartRequest 생성자 : 요청객체,
		          java.lang.String saveDirectory, int maxPostSize,            저장경로, 최대 파일 사이즈,
		          java.lang.String encoding, FileRenamePolicy policy)         인코딩방식, 중복처리 인터페이스 사용 가능 생성자(파일이름이 동일할 경우 이름을 변환해줌) */

   String uploadPath=application.getRealPath("upload");
   //getRealPath(String path(디스크상의 경로)) 웹어플리케이션 내에서 지정하는 경로에 해당하는 자원의 시스템상에서의 경로를 리턴(읽기, 쓰기 모두 가능)
   //getRealPath("/");는 webapp폴더까지..

   int size= 10*1024*1024; //메가바이트(파일 사이즈)
   String name=""; //FileUploadForm에서 넘겨 받은 이름
   String subject=""; //넘겨 받은 제목
   String filename1="";
   String filename2="";   //다운로드 할때 저장되는 이름
   String origfilename1="";  //업로드한 파일 이름
   String origfilename2="";
   try{   
	   // ↓ 아래만 생성해주면 파일이 업로드 된다. (파일자체 업로드 완료))
	   MultipartRequest multi = new MultipartRequest(request, uploadPath, size, "utf-8", new DefaultFileRenamePolicy()); // 생성자 오버라이딩 요소값
	   
	   // MultipartRequest로 전송받은 데이터를 불러온다.
   	   // enctype을 "multipart/form-data"로 선언하고 submit한 데이터들은 request객체가 아닌 MultipartRequest객체로 불러와야 한다.
	
	   //↓ 아래는 전송받은 데이터들을 변수에 저장하기 위함
	   name=multi.getParameter("name");  //해당하는 파라미터의 값을 String 타입으로 반환
	   subject=multi.getParameter("subject"); 
	   Enumeration files = multi.getFileNames(); //폼에서 전송된 file type의 파라미터 이름들을 Enumeration타입으로 반환(배열)
	   String file1 = (String)files.nextElement(); //Enumeration에 저장된 데이터의 다음값(첫번째 파일이름)을 nextElement()메서드로 반환
	   filename1=multi.getFilesystemName(file1); //클라이언트가 업로드한 파일의 실제적으로 업로드 된 이름 반환
	   origfilename1=multi.getOriginalFileName(file1);//클라이언트가 업로드한 파일의 원래 이름을 반환
	   String file2=(String)files.nextElement();  // 두번째 파일 이름
	   filename2=multi.getFilesystemName(file2);
	   origfilename2=multi.getOriginalFileName(file2);
   }catch(Exception e) {
	   e.printStackTrace();
   }
   %>
<html>
<body>
<form name="filecheck" action="fileCheck.jsp" method="post">
<input type="hidden" name="name" value="<%=name %>">   <!-- hidden 값 form테크안에서 submit()과 함께 filecheck.jsp로 넘어감 -->
<input type="hidden" name="subject" value="<%=subject %>">
<input type="hidden" name="filename1" value="<%=filename1 %>">
<input type="hidden" name="filename2" value="<%=filename2 %>">
<input type="hidden" name="origfilename1" value="<%=origfilename1 %>">
<input type="hidden" name="origfilename2" value="<%=origfilename2 %>">
</form>
 <a href="#" onclick="javascript:filecheck.submit()">  <!-- submit버튼없이도, onclick(javascript)를 통해 submit 구현   -->
     업로드 확인 및 다운로드 페이지 이동</a>
</body>
</html>