<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/css/myweb.css">
	<link rel="stylesheet" href="/css/boardStyle.css">
	<link rel="stylesheet" href="/css/writeStyle.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

	<script>
		function init() {
			// var msg = document.getElementsByName("msg").value; // ElementsByName 은 배열로 넘어옴
			var msg = document.getElementsByName("msg");
			//alert(msg);
			var alert_msg;
			var modal_pop = false;
			if(msg[0].value == "loginOk") {
				//alert("로그인이 되었습니다.");
				alert_msg = "로그인이 되었습니다.";
				modal_pop = false;
			}
			
			if(msg[0].value == "loginFail") {
				alert_msg = "로그인이 정보가 없습니다.";
				modal_pop = true;
				//var stat = document.getElementsByName("stat");
				//if(stat[0].value == 1) {
					// alert(stat[0].value);
			}
				
			if(msg[0].value == "memberOk") {
				alert_msg = "회원등록이 되었습니다.";
				modal_pop = true;
					//document.getElementById("modal").style.display ="block";
			}
			if(msg[0].value != "null") alert(alert_msg);
			if(modal_pop) document.getElementById("modal").style.display ="block";
		}
		
	</script>