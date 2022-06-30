<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.awt.Graphics2D" %>
<%@ page import ="java.awt.image.renderable.ParameterBlock" %>
<%@ page import ="java.awt.image.BufferedImage" %>    
<%@ page import ="javax.media.jai.JAI" %>
<%@ page import ="javax.media.jai.RenderedOp" %>
<%@ page import ="javax.imageio.ImageIO" %>
<%@ page import ="com.oreilly.servlet.MultipartRequest" %>
<%@ page import ="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import ="java.util.*" %>
<%@ page import ="java.io.*" %>
<%
      String imagePath = request.getRealPath("upload"); //업로드될 파일 경로 설정
      int size = 1*1024*1024; //업로드 사이즈 제안 10M이하
      String filename = "";
      try{ 
    	    //파일 업로드 완료(원본 이미지)
    	    //MultipartRequest 클래스를 생성하고 request를 통해 thumbnailform에서 업로드한 파일을 imagePath에 저장한다. 
            MultipartRequest multi = new MultipartRequest(request, imagePath, size, "utf-8", new DefaultFileRenamePolicy());
            Enumeration files = multi.getFileNames(); //저장된 파일명을 변수에 저장
            String file = (String)files.nextElement(); //첫번째 파일명을 가져온다.
            filename=multi.getFilesystemName(file); //getFilesystemName를 통해 업로드된(저장된) 파일명을 가져온다. 
      }catch(Exception e){
            e.printStackTrace();
      } //end
      /* 썸네일(리싸이즈작업)SUN사에서 제공하는 강력한 이미지 편집기능을 가진 API로 이미지타입과 효과등을 적용할 수있다.
          - 사용을 위해서는  cos.jar, codec, core
      
      JAI(JAVA Advanced Imaging)API
      S
      
      */
      ParameterBlock pb = new ParameterBlock(); // ParameterBlock 클래스에 
      pb.add(imagePath+"/"+filename);           // 변환할 (경로/)이미지를 담고 (이미지는 ParameterBlock 통해서만 담을 수있다.)
      RenderedOp rOp=JAI.create("fileload", pb);//그 이미지를 불러온다.
      BufferedImage bi = rOp.getAsBufferedImage(); //불러올 이미지를 bi로 생성한 BufferdImage 클래스에 담는다.
      
      //thumb이란 이미지 버퍼를 생성하고 버퍼 사이즈를 100*100로 설정함
      BufferedImage thumb = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB); 
      Graphics2D g = thumb.createGraphics(); //thumb의 createGraphics()메소드 호출로 대상 BufferedImage에 그림을 그릴 수 있는 Graphic2D객체를 구현 / 그래픽스 객체를 저장하고 
      g.drawImage(bi, 0, 0, 100, 100, null); // 썸네일 버퍼공간에 대해 Graphics2D객체를 얻어와서 입력이미지에 있는 
                                             // 내용을 그린다.(0,0위치에 100,100크기로 생성)

      
      File file = new File(imagePath+"/sm_"+filename); //출력파일에 대한 객체를 만들고
      
      ImageIO.write(thumb,"jpg",file); //ImageIO.write()로 출력
%>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 썸네일 예제</title>
</head>
<body>
-원본 이미지-<br>
<img src="/myWeb/upload/<%=filename %>"><p>
-썸네일 이미지-<br>
<img src="/myWeb/upload/sm_<%=filename %>"><p>

</body>
</html>