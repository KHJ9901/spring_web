<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 로그인 팝업 -->
<div id="modal">
	<div class="modal-content">
		<div class="login">
			<a href="javascript:modal_close()">
			<img id="나가기" src="https://cdn-icons-png.flaticon.com/512/458/458594.png">
			</a>

			<form class="loginform" method="post" action="/login">
				<input type="text" placeholder="아이디" maxlength="10" name="id" required><br>
				<input type="password" placeholder="비밀번호" maxlength="10" name="pw" required><br>
				<input class="loginbtn" type="submit" value=" 로그인">
			</form>

			<div class="regis">
			<p id="가입찾기"><a href="/member/memRegForm">회원가입</a> | 
						  <a href="#">비밀번호찾기</a>
			</div>
		</div>
	</div>
</div>

<script>
function loginTag() {
 	document.getElementById("modal").style.display= "block";
} 
function modal_close() {
	document.getElementById("modal").style.display= "none";
}   
</script>