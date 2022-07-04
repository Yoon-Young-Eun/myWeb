package tommy.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

    private static BoardDAO instance = null;
    private BoardDAO() {}
    
    public static BoardDAO getInstance() {
    	
    	if(instance == null) {
    		synchronized(BoardDAO.class) {
    			instance = new BoardDAO();
    		}
    	}
    	return instance;
    }
   //이곳에 게시판 작업의 기능 하나하나를 메소드로 추가 할 것이다.
    
    public int getArticleCount() {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	int x=0;
    	
    	try {
    		conn = ConnUtil.getConnection();
    		pstmt=conn.prepareStatement("select count(*) from board");
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    			x= rs.getInt(1);
    		}
    	}catch (Exception ex) {
    		ex.printStackTrace();
    	}finally {
    		try {
    			if(rs != null)rs.close();
    			if(pstmt != null) pstmt.close();
    			if(conn != null) conn.close();
    		}catch(SQLException e) {
    			
    		}
    	}
    	return x;
    }
    
    public List<BoardVO> getArticles(int start, int end){
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       List<BoardVO>articleList = null;
       try {
    	   conn = ConnUtil.getConnection();
    	   pstmt = conn.prepareStatement("select rownum rnum, num, writer, email, subject, pass, regdate, readcount, ref, step, depth, content,ip from "
    			   +"(select * from board order by ref desc, step, asc))where rnum>=? and rnum<=?");
    	   pstmt.setInt(1, start);
    	   pstmt.setInt(2, end);
    	   rs=pstmt.executeQuery();
    	   if(rs.next()) {
    		   articleList = new ArrayList<BoardVO>(5);
    		   do {
    			 BoardVO article = new BoardVO();
    			 article.setNum(rs.getInt("num"));
    			 article.setWriter(rs.getString("writer"));
    			 article.setEmail(rs.getString("email"));
    			 article.setSubject(rs.getString("subject"));
    			 article.setPass(rs.getString("pass"));
    			 article.setRegdate(rs.getTimestamp("regdate"));
    			 article.setReadcount(rs.getInt("readcount"));
    			 article.setRef(rs.getInt("ref"));
    			 article.setStep(rs.getInt("step"));
    			 article.setDepth(rs.getInt("depth"));
    			 article.setContent(rs.getString("content"));
    			 article.setIp(rs.getString("ip"));
    			 articleList.add(article);
    		   }while(rs.next());
           }
    }catch(Exception ex) {
    	ex.printStackTrace();
    }finally {
    	try {
    		if(rs!= null) rs.close();
    		if(pstmt != null)pstmt.close();
    		if(conn != null)conn.close();
    	}catch(SQLException e) {
    		
    	}
    	
    }
       return articleList;
  }
    
    
    
    
}