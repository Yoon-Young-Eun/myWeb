package tommy.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import tommy.web.memberone.StudentVO;
import tommy.web.memberone.ZipCodeVO;

public class StudentDAO {
	private Connection getConnection() {
		Connection conn = null;

		try {
			Context init = new InitialContext();
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/myOracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.err.println("Connection 생성 실패");
		}
		return conn;
	}

	public boolean idCheck(String id) {
		boolean result = true;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select * from student where id=?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (!rs.next())
				result = false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
		return result;
	}

	public Vector<ZipCodeVO>zipcodeRead(String dong){
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<ZipCodeVO> vecList = new Vector<ZipCodeVO>();
		try {
			conn = getConnection();
			String strQuery ="select * from zipcode where dong like '"+dong+"%'";
			pstmt= conn.prepareStatement(strQuery);
			rs=pstmt.executeQuery();
			
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
			try {if(rs != null)rs.close();}catch(SQLException e) {}
			try {if(pstmt != null)pstmt.close();}catch(SQLException e) {}
			try {if(conn != null)conn.close();}catch(SQLException e) {}
			
		}
		return vecList;
	}

	public boolean memberInsert(StudentVO vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
	try {
		
		conn= getConnection();
		String strQuery="insert into student values(?,?,?,?,?,?,?,?,?,?)";
		pstmt= conn.prepareStatement(strQuery);
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
		if(count>0)flag = true;
	}catch(Exception ex) {
		System.out.println("Exceptio"+ex);
	}finally {
		try {if(rs!=null)rs.close();}catch(SQLException sqle1) {}
		try {if(pstmt!=null)pstmt.close();}catch(SQLException sqle2) {}
		try {if(conn!=null)conn.close();}catch(SQLException sqle3) {}
	}
	return flag;
	
}
}
