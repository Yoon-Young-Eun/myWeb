package tommy.web.sample;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Source")
public class Source extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Source Start");
		//페이지 이동
		//1. forward 방식으로 이동

		RequestDispatcher view = request.getRequestDispatcher("Destination");
		request.setAttribute("myName", "윤영은");
		request.setAttribute("myAge", "30");
		view.forward(request, response);

		//2. redirect 방식으로 이동
		//response.sendRedirect("Destination");


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
