package tommy.mvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {
     private static final long serialVersionUID=1;
     //명령어와 명령어 처리 클래스를 쌍으로 저장
     private Map<String, Object> commandMap = new HashMap<String, Object>();   //지역변수 키값은 문자열로, 밸류는 객체로 저장
     //명령어와 처리클래스가 매핑되어 있는 properties파일을 읽어서 Map객체인 commandMap에 저장
     //명령어와 처리클래스가 매핑되어 있는 properties파일은 Command.properties 파일
     @SuppressWarnings("unchecked") //부적잘한 컴파일러의 경고를 제거하기 위해 사용됨
     public void init(ServletConfig config) throws ServletException{   
    //web.xml에서 propertyConfig에 해당하는 init-param의 값을 읽어 옴
     String props = config.getInitParameter("propertyConfig"); //web.xml에서 properties를 받아오라는 거(Command.properties) 
     //명령어와 처리클래스의 매핑정보를 저장할 Properties객체 생성
     Properties pr = new Properties(); // Properties는 키값과 value가 String 으로 되어있는 특수한 맵이다.
     String path=config.getServletContext().getRealPath("/WEB-INF");  //실제 디스크상 파일 경로..ContextPath = /myWeb, (C:\---\WEB-INF\)
     FileInputStream f = null; //InputStream을 상속받으며, 파일로 부터 바이트로 입력받아 바이트 단위로 출력하는 클래스 
     try {
    	 //Command.properties파일의 내용을 읽어옴
    	 f = new FileInputStream(new File(path, props));   //C:\--\WEB-INF\  + Command.properties
    	 //Command.properties파일의 정보를 Properties객체(pr)에 저장
    	 pr.load(f); //키값 밸류를 넣음
     }catch(IOException e) {
    	 throw new ServletException(e);
     }finally {
    	 if(f != null)try {f.close();}catch(IOException ex) {}
     }
     //Iterator객체는 Enumeration 객체를 확장시킨 개념의 객체
     Iterator<Object> keyIter = pr.keySet().iterator();  //열쇠주머니 만들기,, Map key값-> 모아서 주머니를 만듬 KeyIter(mvc.message.do)
     //객체를 하나씩 꺼내서 그 객체명으로 Properties 객체에 저장된 객체에 접근
     while(keyIter.hasNext()) {
    	 String command = (String)keyIter.next(); //키값을 저장함(key : mvc.message.do    in Command.properties)
    	 String className = pr.getProperty(command);   //키값 밸류를 문자열로 가져옴(value : tommy.mvc.MessageProcess   in Command.properties)
    	 try {//해당 문자열을 클래스로 만든다.
    		 Class commandClass = Class.forName(className); // Class.forName()이러한클래스를 찾으라는,, => tommy.mvc.MessageProcess ->찾아감
    		 Object commandInstance = commandClass.newInstance();//해당 클래스의 객체를 생성  
    		 //newInstance()로,, commandInstance를 실제 객체로 만들어.   
    		 //Map객체인 commandMap에 객체 저장
    		 //키값 : mvc.message.do, 객체 tommy.mvc.MessageProcess
    		 commandMap.put(command, commandInstance);    //키값, 밸류
    	 }catch(ClassNotFoundException e) {
    		 throw new ServletException(e);
    	 }catch(InstantiationException e) {
    		 throw new ServletException(e);
    	 }catch(IllegalAccessException e) {
    		 throw new ServletException(e);
    	 }
      }
    }  //init() 내용은 commandMap안에 키값과 밸류를 넣는것
       // 이후 service() 를 지나 doGet로 가서 요청과 응답을 담아 requestPro()로 보냄
    public void doGet(//get방식의 서비스 메서드
    		HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
    	requestPro(request, response);
    }
    //사용자의 요청을 분석해서 해당 작업을 처리 (commanMap안의 값을 처리 사용하는,,)
	private void requestPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String view = null;
		CommandProcess com= null; //Action에 해당하는 인터페이스
		try {
			String command = request.getRequestURI();  //uri : /myWeb/mvc/message.do
			if(command.indexOf(request.getContextPath())==0) {  //커멘드에서 ContextPath(= "/myWeb")값을 찾아서 시작 인덱스 번호를 리턴하는데 값은 0이다.(없으면-1반환)
			   command = command.substring(request.getContextPath().length());  //  "/myWeb"의 길이=6 
			                   //substring()는 객체의 시작부터 종료까지 문자열의 부분 문자열 반환
                               //6번 이후 즉 7번부터 끝가지 추출한다.  = > /mvc/message.do
			}
			com = (CommandProcess)commandMap.get(command); //init()에서 commadMap 저장한 키값과 동일한 키값인 /mvc/message.do를  com객체에저장 (키값에 해당하는 밸류를 찾기위해)
			view = com.requestPro(request,response); //action의 execute에 해당하는 requestPro()메서드에 요청과 응답값을 저장하여  view에 문자열로 저장
		}catch(Throwable e) {
			throw new ServletException(e);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);  //forward()방식으로 출력하기위해 RequestDispatcher 객체 생성
		dispatcher.forward(request, response); //객체 안에 요청과 응답을 담아 출력   
		 // 키값을 포함한 url은 http://localhost:8080/myWeb/mvc/message.do 이지만, /mvc/message/do 키값의 밸류는 tommy.mvc.MessageProcess으로 MessageProcess로 이동!
		 // MessageProcess 에서는 action역할인 CommandProcess 인터페이스의 requestPro(=execute())를 오버라이딩 하여
		 // request.setAttribute("message", "요청 파라미터로 명령어를 전달"); message라는 키값에 "요청 파라미터로 명령어를 전달"을 담아
		 // return "/mvc/process.jsp";    <- jsp로 보냄!
		 //  최종적으로  process.jsp에서는 message 값을 받아. value를 출력 
	     // 처리 결과: <c:set var="message" value="${requestScope.message }"/>   - >  <c:out value="${message }"/>
		 // 정리하자면.. http://localhost:8080/myWeb/mvc/message.do 라고 치면 밸류 값인 MessageProcess에서 requestPro()메서드를 처리하여
		 // process.jsp로 키값과 문자열을 보내고, jsp에서는 그걸 출력하여 아래와 같이 나나탐
		 // =>  결과: 요청 파라미터로 명령어를 전달
		
	}
}
