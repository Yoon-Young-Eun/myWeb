package tommy.web.memberone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class StudentDAO {
   private static StudentDAO instance=null;// 2. 자신을 멤버필드로 선언해서 메모리에 올려놓는다.(static), 여전히 private...
   private StudentDAO() {}//1. 생성자가 private이면 아무도 객체 생성 못함
   public static StudentDAO getInstance() {//3. 외부에서 멤버로 선메소드
	   if(instance == null) {
		   synchronized(StudentDAO.class) {
			   instance = new StudentDAO();
		   }
	   }
	   return instance;
   }
   
 private Connection getConnection() {
      Connection con = null;
      try {
         Context init = new InitialContext();
         DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/myOracle");
         con = ds.getConnection();
      }catch(NamingException ne) {
         ne.printStackTrace();
      }catch(SQLException se) {
         se.printStackTrace();
      }
      return con;
   }

   public boolean idCheck(String id) {
      boolean result =true;
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
         conn = getConnection();
         pstmt = conn.prepareStatement("select * from student where id = ?");
         pstmt.setString(1, id);
         rs = pstmt.executeQuery();
         if(!rs.next()) {result = false;}
      }catch(SQLException sqle) {
         sqle.printStackTrace();
      }finally {
         try {
            if(rs!=null) {rs.close();}
            if(rs!=null) {pstmt.close();}
            if(rs!=null) {conn.close();}
         }catch(SQLException e) {
            e.printStackTrace();
         }
      }
      return result;
   }
   public Vector<ZipCodeVO> zipcodeRead(String dong){
      Vector<ZipCodeVO> vecList = new Vector<ZipCodeVO>();
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
         conn = getConnection();
         pstmt = conn.prepareStatement("select * from zipcode where dong like '" + dong + "%'");
         rs = pstmt.executeQuery();
         while(rs.next()) {
            ZipCodeVO tempZipcode = new ZipCodeVO();
            tempZipcode.setZipcode(rs.getString("zipcode"));
            tempZipcode.setSido(rs.getString("sido"));
            tempZipcode.setGugun(rs.getString("gugun"));
            tempZipcode.setDong(rs.getString("dong"));
            tempZipcode.setRi(rs.getString("ri"));
            tempZipcode.setBunji(rs.getString("bunji"));
            vecList.add(tempZipcode);
         }
      }catch(SQLException sqle) {
         sqle.printStackTrace();
      }finally {
         try {
            if(rs!=null) {rs.close();}
            if(rs!=null) {pstmt.close();}
            if(rs!=null) {conn.close();}
         }catch(SQLException e) {
            e.printStackTrace();
         }
      }
      return vecList;
   }
   
   public boolean memberInsert(StudentVO vo) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      boolean flag =false;
      try {
         conn = getConnection();
         String sql = "insert into student values(?,?,?,?,?,?,?,?,?,?)";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, vo.getId());
         pstmt.setString(2, vo.getPass());
         pstmt.setString(3, vo.getName());
         pstmt.setString(4, vo.getPhone1());
         pstmt.setString(5, vo.getPhone2());
         pstmt.setString(6, vo.getPhone3());
         pstmt.setString(7, vo.getEmail());
         pstmt.setString(8, vo.getZipcode());
         pstmt.setString(9, vo.getAddress1());
         pstmt.setString(10, vo.getAddress2());
         int count = pstmt.executeUpdate();
         if(count>0) flag = true;
      }catch(SQLException ex) {
         System.out.println("Exception" + ex);
      }finally {
         try {
            if(pstmt != null) {pstmt.close();}
            if(conn != null) {conn.close();}
         }catch (SQLException sqle) {}
      }
      return flag;
   }
   
   
   
   public int loginCheck(String id, String pass) {
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs= null;
	   int check=-1;
	   try {
		   conn=getConnection();
		   String strQuery ="select pass from student where id=?";
		   pstmt = conn.prepareStatement(strQuery);
		   pstmt.setString(1, id);
		   rs=pstmt.executeQuery();
		   if(rs.next()) {
			   String dbPass = rs.getString("pass");
			   if(pass.equals(dbPass))        check=1;
			   else if (pass.isEmpty())       check=2;  //추가
			   else                           check=0;
			   
		   } // 1:로그인 성공, 2: pass 공백, 0:비밀번호 오류, -1:아이디 없음
	   }catch(Exception ex) {
		   System.out.println("Exception" +ex);
	   }finally {
		   try {if(rs != null)rs.close();}catch(Exception e) {}
		   try {if(pstmt != null)pstmt.close();}catch(Exception e) {}
		   try {if(conn != null)conn.close();}catch(Exception e) {}
	   }
	   return check;
   }
   
   public StudentVO getMember(String id) {
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   StudentVO vo = null;
	   try {
		   conn= getConnection();
		   pstmt=conn.prepareStatement("select * from student where id=?");
		   pstmt.setString(1, id);
		   rs=pstmt.executeQuery();
		   if(rs.next()) {// 해당 아이디에 대한 회원이 존재
			   vo= new StudentVO();
			   vo.setId(rs.getString("id"));
			   vo.setPass(rs.getString("pass"));
			   vo.setName(rs.getString("name"));
			   vo.setPhone1(rs.getString("phone1"));
			   vo.setPhone2(rs.getString("phone2"));
			   vo.setPhone3(rs.getString("phone3"));
			   vo.setEmail(rs.getString("email"));
			   vo.setZipcode(rs.getString("zipcode"));
			   vo.setAddress1(rs.getString("Address1"));
			   vo.setAddress2(rs.getString("Address2"));
		   }
	   }catch(Exception ex) {
		   ex.printStackTrace();
	   }finally {
		   try {if(rs != null) rs.close();}catch(SQLException e) {}
		   try {if(pstmt != null) pstmt.close();}catch(SQLException e) {}
		   try {if(conn != null) conn.close();}catch(SQLException e) {}
	   }
	   return vo;
   }
   
   public void updateMember(StudentVO vo) {
	   Connection conn=null;
	   PreparedStatement pstmt =null;
	   try {
		   conn=getConnection();
		   pstmt=conn.prepareStatement("update student set pass=?, phone1=?, phone2=?, phone3=?, email=?, zipcode=?, address1=?, address2=? where id=?");
		   pstmt.setString(1, vo.getPass());
		   pstmt.setString(2, vo.getPhone1());
		   pstmt.setString(3, vo.getPhone2());
		   pstmt.setString(4, vo.getPhone3());
		   pstmt.setString(5, vo.getEmail());
		   pstmt.setString(6, vo.getZipcode());
		   pstmt.setString(7, vo.getAddress1());
		   pstmt.setString(8, vo.getAddress2());
		   pstmt.setString(9,  vo.getId());
		   pstmt.executeUpdate();
	   }catch(Exception ex) {
		   ex.printStackTrace();
	   }finally {
		   try {if(pstmt != null) pstmt.close();}catch(SQLException e) {}
		   try {if(conn != null) conn.close();}catch(SQLException e) {}
	   }
   }
   
   public int deleteMember(String id, String pass) {
	   Connection conn=null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   String dbPass="";//데이터베이스에 실제 저장된 패스워드
	   int result= -1;//결과치
	
	    try {
	    	conn=getConnection();
	    	pstmt=conn.prepareStatement("select pass from student where id=?");
	    	pstmt.setString(1, id);
	    	rs=pstmt.executeQuery();
	    	if(rs.next()) {
	    		dbPass=rs.getString("pass");
	    		if(dbPass.equals(pass)) {//true 본인 확인
	    			pstmt=conn.prepareStatement("delete from student where id=?");
	    		pstmt.setString(1, id);
	    		pstmt.executeUpdate();
	    		result=1;//회원탈퇴 성공
	    		}else { //본인확인 실패- 비밀번호 오류
	    			result=0;
	    		}
	    	}
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {if(rs != null)rs.close();}catch(Exception e) {}
	    	try {if(pstmt != null)pstmt.close();}catch(Exception e) {}
	    	try {if(conn != null)conn.close();}catch(Exception e) {}
	    }
	    
	    return result;
   }
   
   
}