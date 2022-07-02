package tommy.mvc.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tommy.mvc.control.ActionForward;

public class IndexAction implements Action { //내가 만든 액션을 상속 받고

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("IndexAction의 execute() 수행됨!");
		return new ActionForward("index.jsp", false);
	}

}
