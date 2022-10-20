<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<c:set value="${loginUser}" var="loginuser" />

	<div>
		<button type="button" id="disconnectBtn">나가기</button> 
	</div>
	
	<div id="msgArea"></div>
	
	<div> <!-- 특정인물만 들어오기 때문에 상대방 id 필요없음 -->
		<input type="text" id="msg" name="msg" placeholder="메세지를 입력하세요">
		<input type="button" value="전송" id="sendBtn">
	</div>
	
<script>

	var userid = '${loginuser.id}';
	
	var chatNo = '<c:out value="${chatNo}" />';

	var ws;
	
	console.log("채팅방 번호 : " + chatNo);
	
	connect();
	
	function connect() {
		
		ws = new WebSocket("ws://localhost:8099/chatServer");
		
		ws.onopen = function() { // MyHandler 컨트롤러에서 afterConnectionEstablished 메소드 실행함.
			console.log('연결생성');
			sendMsg("enter", "");
			$('#msg').focus();
			//ws.send(JSON.stringify(msg)); //json 형태로 서버에 전송
			//register();
		};
		
		ws.onmessage = function(e) {
			console.log('서버로부터 받은 메세지: ' + e.data);
			addMsg(e.data);
		};
		
		ws.onclose = function() {
			console.log('연결 종료');
		};
		
	}
	
	//받은 메세지 채팅영역에 표시
	function addMsg(data) {
		//alert(msg);
/* 		var chat = $('#msgArea').val();
		chat = chat + "\n" + msg;
		$('#msgArea').val(chat); */
		
		var tag = data;
			tag += '<div style = "margin-bottom:3px;">';
			tag += '<span style="font-size:11px; color:#red;">'; 
			tag += new Date().toLocaleTimeString();
			tag += '</span></div>';
		$('#msgArea').append(tag);
	}
	
	//페이지 로딩 후 바로 connect 호출
	$('#sendBtn').on("click", function() {
		sendMsg("chat", $('#msg').val());
		$('#msg').val('');
		$('#msg').focus();
	});
	
	//엔터 쳐서 메세지 전송 되게끔
	$('#msg').keydown(function() {
		if(event.keyCode == 13){
			sendMsg("chat", $('#msg').val());
			$('#msg').val('');
			$('#msg').focus();
		}
	});
	
	//메세지 전송
	function sendMsg(step, msg) {
		var msg = {
			step : step,
			chatNo : chatNo,
			userid : userid,
			msg : msg
		};
	
		ws.send(JSON.stringify(msg));
	}
	
	//채팅방 나가기 
	$('#disconnectBtn').click(function() {
		location.replace("/chatList");
		sendMsg("out", "");
		
		//서버 @OnClose 호출
		ws.close();
	});
	
</script>

</body>
</html>