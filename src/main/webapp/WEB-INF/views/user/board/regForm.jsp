<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../dbconn.jsp" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="../common.jsp" %>
<title>글 작성 페이지</title>
</head>

<body>
<%@ include file="../header.jsp" %>

<%@ include file="../menu.jsp" %>


<div class="row">
	<div class="leftcolumn">
		<div class="bdbox">
			<div class="bdtitle">
				<strong>게시판 글작성</strong>
					<p> 쾌적한 게시판 환경을 위해 타인에게 혐오감을 주는 표현은 삼가세요!</p>
			</div>
		
				<!-- 파일을 포함할 땐 method="post" enctype="multipart/form-data" 를 해줘야함 -->
			<form method="post" enctype="multipart/form-data" class="wtform" action="/board/register" onsubmit="return check(this)">
				<input class="wt_title" type="text" name="title" maxlength="50" placeholder="제목을 작성하세요.(50자이내)" required><br><br>
				<c:set value="${loginUser}" var="id" />
					<input type="hidden" value="${id.id}" name="id">
		   		공개여부<input id="wt_sec" type="radio" name="open" value="y">공개
				<input id="wt_sec" type="radio" name="open" value="n">비공개<br><br>
				<input class="wt_content" type="text" name="content" maxlength="1000" placeholder="내용을 기입하세요(1000자이내)" required>
				<input type="file" name="filename"> <!-- 보드컨트롤러 register의 MultipartFile files 매개변수임 -->
			
				<div class="listbt">
					<a href="boardPage.jsp" class="on">목록</a>
					<input type="submit" value="글등록">
				</div>
			
			</form>
		</div>
	</div>
	
	<script>
		function check(f) {
			if(f.wt_open.value == "") {
				alert("공개여부를 확인하세요");
				return false;
			}
			return true;
		}
	</script>

  <div class="rightcolumn">
    <div class="card">
      <h2>About Me</h2>
      <div class="fakeimg" style="height:100px;">Image</div>
      <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
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

<%@ include file="../member/login_modal.jsp" %>

	




</body>
</html>