<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 로그인</title>
<link rel="stylesheet" href="/css/admin.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script>
//제이쿼리
//회원정보가 없습니다를 아이디나 패스워드 클릭하면 사라지게11
/* 	$(document).ready(function() {
		$("html").click(function(e) {
			$("p").hide();
		});
	}); */
</script>
<script>
//제이쿼리
//회원정보가 없습니다를 아이디나 패스워드 클릭하면 사라지게22
$(document).ready(function() {
	document.getElementById("id").onfocus = function(e) {
		document.getElementById("msg").innerHTML = "";
	}
});
</script>
<script>
	function formCheck() {
		var id = document.forms["login"]["id"].value;
		if (id == "") {
			alert("아이디를 입력하세요.");
			return false;
		}

		//비밀번호 체크 
		var pw = document.forms["login"]["pw"].value;
		if (pw == "") {
			alert("비밀번호를 입력하세요.");
			return false;
		} 
		
/*  		var id = document.forms["login"]["id"].value;
		if (id != "id") {
			alert("아이디가 틀렸습니다.");
			return false;
		} */
	}
</script>
</head>

<body>
	<form class="form" name="login" method="post" action="login" onsubmit="return formCheck()">
		<div style="text-align: center;">
			<label>관리 시스템</label><br>
			<br>
			<input type="text" id="id" name="id" placeholder="아이디"><br> 
			<input type="password" id="pw" name="pw" placeholder="비밀번호 "><br>
				<p id="msg">
					<c:if test="${msg != null}" >회원정보가 없습니다.</c:if> 
				</p>
			<br> <br> <input class="loginbtn" type="submit" value="로그인">
		</div>
	</form>
</body>
</html>