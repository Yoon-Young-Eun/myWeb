<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%!
   private int numOne =0;
   public void jspInit(){//메소드 재정의
	   System.out.println("jspInit() 호출됨");
   }
   public void jspDestroy(){//메소드 재정의
	   System.out.println("jspDestroy() 호출됨");
   }
  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP Life Cycle</title>
</head>
<body>
 <%
     int numTwo= 0;
      numOne++; //새로고침할때 증가
      numTwo++; //새로고침할때마다 초기화
      System.out.println(session.getId());
 %>
 <ul>
     <li> Number One : <%=numOne %></li>
     <li> Number Two : <%=numTwo %></li>
     
 </ul>

</body>
</html>