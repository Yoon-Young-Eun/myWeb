package tommy.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//��ɾ� ó�� Ŭ����
public class MessageProcess implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
	/*
	 * ���� ���⿡�ٰ� �ʿ信 ���� VO, DAO�� �ʱ�ȭ�ϰ�
	 * ������ ���̽��� �����ϰ� �Ʒ��� ���� ��� ���� �����Ѵ�.
	 * �׸��� �̵��� ������ �̸��� �Ʒ��� ���� �Ѱ��ش�.
	 */
		request.setAttribute("message", "��û �Ķ���ͷ� ��ɾ ����");
		return "/mvc/process.jsp";
	}
	

}
