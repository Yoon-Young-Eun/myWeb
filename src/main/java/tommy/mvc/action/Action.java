package tommy.mvc.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tommy.mvc.control.ActionForward;

public interface Action {  //ActionForward ∏Æ≈œ
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)throws IOException;
	

}
