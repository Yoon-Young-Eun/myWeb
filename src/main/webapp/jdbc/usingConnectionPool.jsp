<%@page import="tommy.web.jdbc.ConnectionPool"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<% 

ConnectionPool pool = ConnectionPool.getInstance();
Connection conn= null;
Statement stmt =null;
ResultSet rs = null;
String id ="", passwd="", name="", mem_num1="", mem_num2="", e_mail="", phone="",
          zipcode="", address="", job="";
int counter=0;

try{
  conn = pool.getConnection();
  stmt = conn.createStatement();
  rs=stmt.executeQuery("select * from tempmember");
  
  %>

<html>

<head>
<title>JSP에서 데이터 베이스 연동</title>
<link href="style,css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#FFFFCC">
   <h2>JSP 스크립틀릿에서 데이터 베이스 연동 예제 입니다....</h2>
   <br></br>
   <h3>회원정보</h3>

   <table bordercolor="#0000ff" border="1">
      <tr>
         <td><strong>id</strong></td>
         <td><strong>passwd</strong></td>
         <td><strong>name</strong></td>
         <td><strong>mem_num1</strong></td>
         <td><strong>mem_num2</strong></td>
         <td><strong>e_mail</strong></td>
         <td><strong>phone</strong></td>
         <td><strong>zipcode/address</strong></td>
         <td><strong>job</strong></td>
      </tr>
<% 
   if(rs!=null){
       while(rs.next()){
      id = rs.getString("id");
      passwd =rs.getString("passwd");
      name =rs.getString("name");
      mem_num1=rs.getString("mem_num1");
      mem_num2=rs.getString("mem_num2");
      e_mail=rs.getString("e_mail");
      phone=rs.getString("phone");
      zipcode=rs.getString("zipcode");
      address=rs.getString("address");
      job=rs.getString("job");
   
%>

      <tr>
         <td><%=id %></td>
         <td><%=passwd %></td>
         <td><%=name %></td>
         <td><%=mem_num1 %></td>
         <td><%=mem_num2 %></td>
         <td><%=e_mail %></td>
         <td><%=phone %></td>
         <td><%=zipcode %></td>
         <td><%=address %></td>
         <td><%=job %></td>
      </tr>
      <%
          counter++;
      }//end while
   }//end if
%>
</table><br></br>
total records : <%=counter %>
<%
}catch(SQLException sqlException){
   System.out.println("sql exception");
}catch(Exception exception){
   System.out.println("exception");
}finally{
   try{ if(rs != null)rs.close();}catch(SQLException ex){}
   try{ if(stmt != null)stmt.close();}catch(SQLException ex){}
   try{ if(conn != null)pool.releaseConnection(conn);}catch(SQLException ex){}
   
}
%>

</body>



</html>