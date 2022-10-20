<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<%@ include file = "../common.jsp" %>
	<title>회원가입</title>
</head>

<body>

<%@ include file="../header.jsp" %>

<script src="/js/formCheck.js"></script>


<%@ include file="../menu.jsp" %>
	
	<div class="row">
	  <div class="leftcolumn">
	    <div class="card">
	      <h2>${msg}</h2>
	      	<hr>
			<form name="memRegForm" class="memregform" method="post" action="/member/register" onsubmit="return formCheck()">
				<fieldset class="upper">
					<legend> 기본 정보 </legend>
						<label for="id">아이디 : </label><br>
							<input id="id" type="text" name="id" maxlength="10" required onchange="idCheck()">
							<input type="hidden" id="isIdCheck">
							<p id="idCheckMsg"></p>

						<label for="pw">비밀번호 : </label><br>
						<input id="pw" type="password" name="pw" maxlength="10" required><br><br>

						<label for="rpw">비밀번호 재입력 : </label><br>
						<input id="rpw" type="password" name="repw" maxlength="10" required><br><br>

						<label for="name">이름 : </label><br>
						<input id="name" type="text" name="name">
						<span id="msg"></span><br><br>

						<label for="birth">생년월일 : </label><br>
						<input id="birth" type="date" name="birth"><br><br>
				</fieldset>

				<fieldset class="gen">
					<legend>성  별</legend>
				  	<input type="radio" name="gender" value="M"> 남성
				  	<input type="radio" name="gender" value="F"> 여성
				  	<span id="gen"></span>
				</fieldset>
				
			  	<fieldset id="필드">
				  	<legend>취미</legend>
				  	<input type="checkbox" name="hobby" value="테니스"> 테니스
				  	<input type="checkbox" name="hobby" value="드럼"> 드럼
				  	<input type="checkbox" name="hobby" value="명상"> 명상
			  	</fieldset>
			
				<div class="email">
				  	<input type="text" placeholder="이메일아이디" name="eid">@
				  	<input type="text" placeholder="도메인주소" name="domain">
				  	<select name="selDomain" onchange="inputDomain()">
				  		<option value="">직접작성</option>
				  		<option value="gmail.com">gmail.com</option>
				  		<option value="naver.com">naver.com</option>
				  		<option value="daum.net">daum.net</option>
				  		<option value="nate.com">nate.com</option>
				  	</select>
			  	</div>
			  	
			  	<textarea rows="5" cols="50" placeholder="자기소개(최대500자)" name="intro"></textarea>
			  	<input type="submit" value="회원등록">
			</form>

	    </div>
	  </div>
	  <div class="rightcolumn">
	    <div class="card">
	      <h2>About Me</h2>
	      <div class="fakeimg" style="height:100px;">Image</div>
	      <p>Some text..</p>
	    </div>
	    <div class="card">
	      <h3>Popular Post</h3>
	      <div class="fakeimg"><p>Image</p></div>
	      <div class="fakeimg"><p>Image</p></div>
	      <div class="fakeimg"><p>Image</p></div>
	    </div>
	    <div class="card">
	      <h3>Follow Me</h3>
	      <p>Some text..</p>
	    </div>
	  </div>
	</div>
	
<%@ include file="../footer.jsp" %>

<%@ include file="login_modal.jsp" %>

</body>
</html>