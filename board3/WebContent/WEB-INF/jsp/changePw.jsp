<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 수정</title>
</head>
<body>
	${msg }
	<div>비밀번호 변경페이지</div>
	<form id = "frm" action="/myPage?typ=${param.typ}" method="post" onsubmit="return chk()">
		<div><input type = "password" name="currentPw" placeholder = "원래 비밀번호" value="" required></div>
		<div><input type = "password" name="changePw" placeholder = "바꿀 비밀번호" value="" required></div>
		<div><input type = "password" name="cpw2" placeholder = "비밀번호 확인" required></div>
		<input type = "submit">
	</form>
	<script>
	function chk(){
		if(frm.cpw2.value !=frm.changePw.value){
			alert('바꿀 비밀번호가 같지않습니다.')
			return false
		}
	}
	</script>
</body>
</html>