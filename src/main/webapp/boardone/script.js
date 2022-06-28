function writeSave(){
	console.log("0");
	if(document.writeForm.writer.value==""){
		alert("작성자를 입력하십시오.");
		document.writeForm.writer.focus();
			console.log("1");
		return false;
	}
	
		if(document.writeForm.email.value==""){
		alert("이메일을 입력하십시오.");
		document.writeForm.email.focus();
			console.log("2");
		return false;
	}
	
		if(document.writeForm.subject.value==""){
		alert("제목을 입력하십시오.");
		document.writeForm.subject.focus();
			console.log("3");
		return false;
	}
	
	
		if(document.writeForm.content.value==""){
		alert("내용을 입력하십시오.");
		document.writeForm.content.focus();
			console.log("4");
		return false;
	}
	
		if(document.writeForm.pass.value==""){
		alert("비밀번호를 입력하십시오.");
		document.writeForm.pass.focus();
		console.log("5");
		return false;
	}
}