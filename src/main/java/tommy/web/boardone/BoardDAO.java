package tommy.web.boardone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

	private static BoardDAO instance = null; 
	//static���� �ν��Ͻ�ȭ���� �ʰ� �����Ͽ�����, ���������ڰ� private���� �Ǿ� �־� �������� ���� �Ұ�
	private BoardDAO() { //�����ڵ� private�� �ٿ� new�� ���� ��ü ������ �Ұ�
	}
	public static BoardDAO getInstance() { //�ᱹ getInstance�޼��带 ���ؼ� �ش� �ν��Ͻ��� ���� �� �ִ�.
		if(instance == null) {
			synchronized(BoardDAO.class) { //���ÿ������� ���°��� ���� �Ѹ��� ������ ���� ����
				instance = new BoardDAO();
			}
		}
		return instance;
	}

	public void insertArticle(BoardVO article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		//����� ����� 1�� �۾� ������ ref, step, depth�� ����
		int num = article.getNum();
		int ref= article.getRef();
		int step = article.getStep();
		int depth = article.getDepth();
		
		int number = 0; //����� ��� num�� ����ϱ� ���� �ӽú���
		
		String sql ="";

		try {
			conn=ConnUtil.getConnection();
			pstmt=conn.prepareStatement("select max(num) from board");
			rs = pstmt.executeQuery();
			if(rs.next()) number = rs.getInt(1) + 1; //�������� ������
			else number =1;
			if(num != 0) {//�亯���� ���
				// 2. ���� �׷�(ref)������ ������ step�� ū �Ϳ� ���ؼ� +1
				sql = "update board set step=step+1 where ref=? and step > ?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, step);
				pstmt.executeUpdate();
				// 3. ���� step+1, depth+1
				step =step + 1;
				depth=depth +1;
			}else {//�� ���� ���
				ref=number;
				step=0;
				depth=0;
			}//������ �ۼ�
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

	public List<BoardVO> getArticles(int start, int end){  //���̺��� �����͸� ������ �޼���
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
				articleList = new ArrayList<BoardVO>(end-start+1); //����<4>
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
	
	// �۳����� �������鼭 ��ȸ�� +1 -> ��ȸ�� ���� �κ��� upCount(int num) �и�
	public BoardVO getArticle(int num) { //db���� �ϳ��� ������ ���� ���� �޼���
		Connection conn= null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		BoardVO article=null;
		try {
			conn=ConnUtil.getConnection();
			// void upCount(int num); ��ȸ���� �����ϴ� �޼��� �и�
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
	
	//��ȸ���� ������Ű�� �ʰ� �� �޼��带 �����;� ��
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
		int result = -1;//��� ��
		try {
			
			conn = ConnUtil.getConnection();
			pstmt=conn.prepareStatement("select pass from board where num = ?");
			pstmt.setInt(1, article.getNum());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dbpasswd = rs.getString("pass"); //��й�ȣ ��
				if(dbpasswd.equals(article.getPass())) {
					sql ="update board set writer = ? , email = ?, subject= ?, content=? where num = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, article.getWriter());
					pstmt.setString(2, article.getEmail());
					pstmt.setString(3, article.getSubject());
					pstmt.setString(4, article.getContent());
					pstmt.setInt(5,  article.getNum());
					pstmt.executeUpdate();
					result= 1; //���� ����
				}else {
					result=0;//���� ����
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
					result=1;//�ۻ��� ����
				}else
					result=0;//��й�ȣ Ʋ��
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
