<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
 public String getParam(HttpServletRequest request, String paramName){
     
      if(request.getParameter(paramName)!=null){   //데이터 입력시 값을 받아 브라우저에 띄운다.
            return request.getParameter(paramName);
            }else{
            return "";
            }
            }
            %>
<% 
  request.setCharacterEncoding("utf-8");
  int filecounter=0;    
  if(request.getParameter("addcnt")!=null){  //추가할 파일 수를 입력하면, 입력 수를 브라우저에 띄우고, 값을 int로 받아 변수에 저장
      filecounter = Integer.parseInt(request.getParameter("addcnt"));
     }
     %>
<html>
<head>
<title>File Select Page</title>
<link href="style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function inputValue(form1, param, form2, idx){  //frmName1 form테그에서 text에 입력시 frmName1의 변수와 인덱스 값을 이용해
	var paramValue = form1.elements[idx].value; //frmName2에 값을 저장
	form2.elements[idx].value=paramValue;
	return;
}

function addFile(formName){  //formName1 확인 버튼을 눌렀을 때 동작
	if(formName.addcnt.value==""){
	  alert("입력한 파일 개수를 입력하고 확인버튼을 눌러주세요");
	  formName.addcnt.focus();
	  return;
	}
	formName.submit();  //입력된 숫자가 있으면, 해당 변수값 주소를 현재페이지에 박는다.
	                    //post라 보이진 않지만 url에 데이터가 각인되어있어 새로고침해도 변경되지 않는다.
}                       //action이 없으면 submit은 현재페이지에 적용

function elementCheck(formName){ //frmName2 DONE 버튼을 누를때 변수값을 받아 동작 
	 paramIndex=1;
	 for(idx=0; idx<formName.elements.length; idx++){ //selectFile 길이 만큼(추가파일수)
		 if(formName.elements[idx].type == "file"){   //file type일 경우
			 if(formName.elements[idx].value==""){    //추가한 파일행에 누락된 정보가 없는 지 확인하는 과정
				 var message = paramIndex+"번째 파일정보가 누락되었습니다. \n업로드할 파일을 선택해 주세요.";
				 alert(message);
				 formName.elements[idx].focus();
				 return;
			 }
			 paramIndex++;  
		 }
	 }
	 formName.action="fileview.jsp";  // 
	 formName.submit(); // 모든 파일이 다 업로드 되었다면 action의 경로로 파일정보 및 paraName정보를 fileview에 전송한다.
}
</script>
</head>
<body topmargin="100">
<div align="center"><font color="#0000ff" size="2">
복수개의 파일의 업로드를 위해 파일 갯수를 입력한 후<br>
확인 버튼을 눌러주세요!!<br>
입력이 완료되면 DONE 버튼을 눌러주세요</font></div><br>
<form name="frmName1" method="post">
<table width="75%" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#660000" bgcolor="#FFFF99">
<tr bgcolor="#FFCC00">
  <td width="10%"><div align="right">user</div></td> <!-- onkeyup(keyCode값) : 사용자가 키보드의 키를 눌렀다가 땠을 때 -->
  <td><input type="text" name="user" onkeyup="inputValue(this.form,user,frmName2,0)" value="<%=getParam(request, "user") %>"></td>
  <td width="10%"><div align="right">title</div></td>
  <td><input type="text" name="title" onkeyup="inputValue(this.form,title,frmName2,1)" value="<%=getParam(request, "title") %>"></td>
</tr>
<tr bgcolor="#FFCC00">
  <td width ="15%"><div align="right">Abstract</div></td>
  <td width ="50%" colspan="3">
  <textarea name="abstract" cols="40" onkeyup="inputValue(this.form,abstract,frmName2,2)">
  <%=getParam(request,"abstract") %></textarea>
  </td>
</tr>
<tr>
  <td colspan="4"><div align="center">
  <font size="-2">추가할 파일 수 입력</font>
  <input type="Text" name="addcnt">
  <input type="button" value="확인" onclick="addFile(this.form)">
  </div></td>
</tr>
</table>
</form> 
<form name="frmName2" method="post" enctype="multipart/form-data">
<table width="75%" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#660000" bgcolor="#FFFF99">
<tr bgcolor="#FFCCOO">
  <td width="40%">
  <input type="hidden" name="txtUser" value="<%=getParam(request,"user") %>">  <!-- hidden 데이터는 fileview.jsp에 숨겨져 넘어감-->
  <input type="hidden" name="txtTitle" value="<%=getParam(request,"title") %>">
  <input type="hidden" name="txtAbstract" value="<%=getParam(request,"abstract") %>">
<% for(int i=0; i<filecounter; i++){ %>
<input type="File" size="50" name="selectFile<%=i%>"><br>
<%} %>
</td>
<td><input type="Button" value="DONE" onclick="elementCheck(this.form)"></td>
</tr>
</table></form>
</body>
</html>