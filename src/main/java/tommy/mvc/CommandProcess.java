package tommy.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * �������� ��ɾ �����ϴ� ������� ���� Action �������̽��� �ش��ϴ� ���� �������̽���.
 */
//��û �Ķ���ͷ� ��ɾ �����ϴ� ����� ���� �������̽�
public interface CommandProcess {
	/*
	 * ������ ��ɾ �����ϴ� ������� ����
	 * execute �޼��忡 �ش��ϴ� ��
	 */
   public String requestPro(HttpServletRequest request, HttpServletResponse response)throws Throwable;
   
}
