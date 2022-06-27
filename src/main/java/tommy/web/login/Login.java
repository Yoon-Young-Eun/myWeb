package tommy.web.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet { 
	private static final long serialVersionUID =   1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8"); 
		//setContentType, 응답으로 보내질 컨텐츠의 타입과 캐릭터 셋을 지정한다.(HttpServletResponse의 메소드)
		PrintWriter out = response.getWriter(); 
		// PrintWriter - getWriter() 요청에 대한 문자 출력용 스트림을 얻는다.(HttpServletResponse의 메소드)

		try {
			HttpSession session = request.getSession(false); //getSession(boolean value) : HttpSession(인터페이스) 객체를 반환한다. 
			//false로 지정된 경우 해당 클라이언트에 대해 생성된 객체가 없으면 null을 반환한다.
			if(session != null) {
				String sessionId = session.getId(); //getId()세션 아이디 불러오기
				System.out.println("session ID : " + sessionId);
				String user = (String)session.getAttribute("user");

				out.println("<html>");  
				out.println("<body>");
				out.println("<table border = '1' width = '300'>");
				out.println("<tr>");
				out.println("<td width = '300' align = 'center'>" + user + " is logged in.</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td align = 'center'>");
				out.println("<a href ='#'>User Information</a>");
				out.println("<a href = 'Logout'>Logout</a>");
				out.println("</td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("</body>");
				out.println("</html>");
			}else {
				out.println("<html>");
				out.println("<body>");
				out.println("<form method = 'post' action = 'LoginCheck'>");
				out.println("<table border = '1' widdth = '300'>");
				out.println("<tr>");
				out.println("<th width = '100'>ID</th>");
				out.println("<td width = '200'>&nbsp;<input type = 'text' name = 'id'></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width = '100'>PASSWORD</th>");
				out.println("<td width = '200'>&nbsp;<input type ='password' name = 'pwd'></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td align = 'center' colspan = '2'>");
				out.println("<input type = 'button' value = 'JOIN US'>");
				out.println("<input type = 'submit' value = 'LOGIN'>");
				out.println("</td>");
				out.println("</tr>");
				out.println("</form>");
				out.println("</table>");
				out.println("</body>");
				out.println("</html>");
			}
		}finally {
			out.close();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}