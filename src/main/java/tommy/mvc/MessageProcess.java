package tommy.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//명령어 처리 클래스
public class MessageProcess implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
	/*
	 * 원래 여기에다가 필요에 따라서 VO, DAO를 초기화하고
	 * 데이터 베이스를 연동하고 아래와 같이 결과 값을 세팅한다.
	 * 그리고 이동할 페이지 이름을 아래와 같이 넘겨준다.
	 */
		request.setAttribute("message", "요청 파라미터로 명령어를 전달");
		return "/mvc/process.jsp";
	}
	

}
