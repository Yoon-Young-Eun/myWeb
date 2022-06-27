package tommy.web.sample;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LifeCycle extends HttpServlet {
	private String company;
	private String manager;
	private String tel;
	private String email;

	//생성자  
	public LifeCycle() {
		System.out.println("LifeServlet의 생성자 호출됨..");
	}

	@Override
	//LifeServlet의 초기화 작업을 담당한다.
	//Servlet객체 생성시 단 한번 수행됨
	public void init() throws ServletException{
		System.out.println("init() 호출됨..");
		//ServletContext의 초기 파라미터 값 읽기
		company = getServletContext().getInitParameter("company");
		manager = getServletContext().getInitParameter("manager");
		//ServletConfig의 초기 파라미터 값 읽기
		tel= getServletConfig().getInitParameter("tel");
		email=getServletConfig().getInitParameter("email");
	}

	@Override
	//LifeServlet 객체가 WAS에서 소멸될때 수행됨
	public void destroy() {
		System.out.println("destroy() 호출됨...");
	}

	@Override
	//클라이언트의 연결요청이 있을때 마다 호출된다.
	protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		System.out.println("service() 호출됨...");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try{
			response.setContentType("text/html;charset=euc-kr");
			out.println("<html>");
			out.println("<body>");
			out.println("<li> 회사명 : "+company+"</li>");
			out.println("<li> 담당자 :"+manager+"</li>");
			out.println("<li> 전화번호 : "+tel+"</li>");
			out.println("<li> 이메일 : "+email+"</li>");
			out.println("</body>");
			out.println("</html>");
		}finally {
			out.close();
		}
	}
}
