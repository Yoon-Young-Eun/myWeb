package tommy.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyServlet extends HttpServlet {//servlet 만들때 GenericServlet, HttpServlet 둘중 하나를 상속 (http가 자손격으로 더 많은 기능,, 많이 사용
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	//get = do get / post = do Post
	//서블릿의 lifecycle : 생성자 -> init() -> service()의 역할 get방식 요청 - doGet, post방식이면 doPost -> destory();
			throws IOException, ServletException{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		try{
			out.println("<html><head><title>My First Servlet</title></head><body>");
			out.println("<h1 color='red'><br><center><font size='5'>지금은");
			out.println(new java.util.Date());
			out.println(" 입니다. </font></center></body></html>");
		}finally{
			out.close();
		}
	}
}
