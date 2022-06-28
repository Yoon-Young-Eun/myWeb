package tommy.web.boardone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

	private static BoardDAO instance = null; 
	//static으로 인스턴스화하지 않고 쓰게하였으나, 전근제한자가 private으로 되어 있어 직접적인 접근 불가
	private BoardDAO() { //생성자도 private을 붙여 new를 통한 객체 생성도 불가
	}
	public static BoardDAO getInstance() { //결국 getInstance메서드를 통해서 해당 인스턴스를 얻을 수 있다.
		if(instance == null) {
			synchronized(BoardDAO.class) { //동시에여러명 들어가는것을 막고 한명이 끝나면 다음 차례
				instance = new BoardDAO();
			}
		}
		return instance;
	}

	public void insertArticle(BoardVO article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		//답글일 경우의 1번 작업 원글의 ref, step, depth를 복사
		int num = article.getNum();
		int ref= article.getRef();
		int step = article.getStep();
		int depth = article.getDepth();
		
		int number = 0; //답글일 경우 num을 계산하기 위한 임시변수
		
		String sql ="";

		try {
			conn=ConnUtil.getConnection();
			pstmt=conn.prepareStatement("select max(num) from board");
			rs = pstmt.executeQuery();
			if(rs.next()) number = rs.getInt(1) + 1; //기존글이 있으면
			else number =1;
			if(num != 0) {//답변글일 경우
				// 2. 같은 그룹(ref)내에서 나보다 step이 큰 것에 대해서 +1
				sql = "update board set step=step+1 where ref=? and step > ?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, step);
				pstmt.executeUpdate();
				// 3. 나의 step+1, depth+1
				step =step + 1;
				depth=depth +1;
			}else {//새 글일 경우
				ref=number;
				step=0;
				depth=0;
			}//쿼리를 작성
			sql="insert into board(num, writer, email, subject, pass, regdate, ref, step, depth, content, ip) values(board_seq.nextval,?,?,?,?,?,?,?,?,?,?)";

			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, article.getWriter());
			pstmt.setString(2,  article.getEmail());
			pstmt.setString(3, article.getSubject());
			pstmt.setString(4, article.getPass());
			pstmt.setTimestamp(5,article.getRegdate());
			pstmt.setInt(6, ref);
			pstmt.setInt(7,  step);
			pstmt.setInt(8, depth);
			pstmt.setString(9, article.getContent());
			pstmt.setString(10, article.getIp());
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			}catch(Exception e) {
			}
		}
	}

	public int getArticleCount() {
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		int x =0;
		try {
			conn= ConnUtil.getConnection();
			pstmt=conn.prepareStatement("select count(*) from board");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				x=rs.getInt(1);

			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			}catch(SQLException e) {
			}
		}
		return x;
	}

	public List<BoardVO> getArticles(int start, int end){  //테이블에서 데이터를 가져올 메서드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> articleList = null;
		try {
			conn=ConnUtil.getConnection();
			pstmt= conn.prepareStatement("select * from (select rownum rnum, num, writer, email, subject, pass, regdate, readcount, ref, step, depth, content, ip from (select * from board order by ref desc, step asc)) where rnum >= ? and rnum <= ?");
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				articleList = new ArrayList<BoardVO>(end-start+1); //수정<4>
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
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			}catch(SQLException e) {
			}

		}
		return articleList;
	}
	
	// 글내용을 가져오면서 조회수 +1 -> 조회수 증가 부분을 upCount(int num) 분리
	public BoardVO getArticle(int num) { //db에서 하나의 정보를 가져 오는 메서드
		Connection conn= null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		BoardVO article=null;
		try {
			conn=ConnUtil.getConnection();
			// void upCount(int num); 조회수만 증가하는 메서드 분리
			pstmt=conn.prepareStatement(
					"update board set readcount=readcount + 1 where num =?");
			pstmt.setInt(1,num);
			pstmt.executeUpdate();
			pstmt= conn.prepareStatement("select * from board where num = ?");
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				article=new BoardVO();
				article.setNum(rs.getInt("num"));
				article.setWriter(rs.getString("writer"));
				article.setEmail(rs.getString("email"));
				article.setSubject(rs.getString("subject"));
				article.setPass(rs.getString("pass"));
				article.setRegdate(rs.getTimestamp("regdate"));
				article.setReadcount(rs.getInt("Readcount"));
				article.setRef(rs.getInt("ref"));
				article.setStep(rs.getInt("step"));
				article.setDepth(rs.getInt("depth"));
				article.setContent(rs.getString("content"));
				article.setIp(rs.getString("ip"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try { 
				if(rs != null) rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			}catch(SQLException e) {
		}
		
	}return article;
}
	
	//조회수를 증가시키지 않고 글 메서드를 가져와야 함
	public BoardVO updateGetArticle(int num) { 
		Connection conn= null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		BoardVO article=null;
		try {
			conn=ConnUtil.getConnection();
			pstmt=conn.prepareStatement("select * from board where num = ?");
			pstmt.setInt(1,num);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				article=new BoardVO();
				article.setNum(rs.getInt("num"));
				article.setWriter(rs.getString("writer"));
				article.setEmail(rs.getString("email"));
				article.setSubject(rs.getString("subject"));
				article.setPass(rs.getString("pass"));
				article.setRegdate(rs.getTimestamp("regdate"));
				article.setReadcount(rs.getInt("Readcount"));
				article.setRef(rs.getInt("ref"));
				article.setStep(rs.getInt("step"));
				article.setDepth(rs.getInt("depth"));
				article.setContent(rs.getString("content"));
				article.setIp(rs.getString("ip"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try { 
				if(rs != null) rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			}catch(SQLException e) {
		}
		
	}return article;
}
	public int updateArticle(BoardVO article) {
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbpasswd="";
		String sql="";
		int result = -1;//결과 값
		try {
			
			conn = ConnUtil.getConnection();
			pstmt=conn.prepareStatement("select pass from board where num = ?");
			pstmt.setInt(1, article.getNum());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dbpasswd = rs.getString("pass"); //비밀번호 비교
				if(dbpasswd.equals(article.getPass())) {
					sql ="update board set writer = ? , email = ?, subject= ?, content=? where num = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, article.getWriter());
					pstmt.setString(2, article.getEmail());
					pstmt.setString(3, article.getSubject());
					pstmt.setString(4, article.getContent());
					pstmt.setInt(5,  article.getNum());
					pstmt.executeUpdate();
					result= 1; //수정 성공
				}else {
					result=0;//수정 실패
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			}catch(SQLException e) {
				
			}
		} return result;
	}
	
	public int deleteArticle(int num, String pass) {
		
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbpasswd = "";
		int result= -1;
		
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement("select pass from board where num = ?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dbpasswd = rs.getString("pass");
				if(dbpasswd.equals(pass)) {
					pstmt = conn.prepareStatement("delete from board where num=?");
					pstmt.setInt(1, num);
					pstmt.executeUpdate();
					result=1;//글삭제 성공
				}else
					result=0;//비밀번호 틀림
				}
			}catch(Exception e) {
				e.printStackTrace();
		}finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null)rs.close();
				if(conn != null)conn.close();
			}catch(SQLException e) {
				
			}
			
		}return result;
	}
	
}
