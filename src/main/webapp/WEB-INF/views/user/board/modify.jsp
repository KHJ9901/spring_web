<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="../dbconn.jsp" %>


<!DOCTYPE html>
<html>
<head>
<%@ include file="../common.jsp" %>
<title>글 수정 페이지</title>
</head>

<body>
<%@ include file="../header.jsp" %>

<%@ include file="../menu.jsp" %>


<div class="row">
	<div class="leftcolumn">
		<div class="bdbox">
			<div class="bdtitle">
			<c:set value="${board}" var="board" />
				<strong>${board.seqno}번째 글 수정</strong>
					<p> 쾌적한 게시판 환경을 위해 타인에게 혐오감을 주는 표현은 삼가세요!</p>
			</div>
		
			<form class="wtform" method="post" enctype="multipart/form-data" action="/modify.bo">
				<input type="hidden" name="seqno" value="${board.seqno}">
				<input class="wt_title" type="text" name="wt_title" 
					   maxlength="50" required value="${board.title}"><br><br>
		   공개여부<input id="wt_sec" type="radio" name="wt_open" value="y" 
		   			<c:if test="${board.open == 'y'}"> checked </c:if> >공개
		   			
				<input id="wt_sec" type="radio" name="wt_open" value="n" 
					<c:if test="${board.open == 'n'}"> checked </c:if> >비공개<br><br>
					
				<input class="wt_content" type="text" name="wt_content" maxlength="1000" 
					   required value="${board.content}">
				<br>
				<!-- 첨부파일 -->
				<c:set value="${board.attachfile}" var="attachfile" />
				<c:choose>
					<c:when test="${empty attachfile}">
						<input type="file" name="filename">
					</c:when>
					
					<c:when test="${!empty attachfile}">
						<c:forEach items="${attachfile}" var="file">
							<c:set value="${file.fileType}" var="filetype" />
							<c:set value="${fn:substring(filetype, 0, fn:indexOf(filetype, '/'))}" var="type" />
							
							<div id="fileSector">
							
								<c:if test="${type eq 'image'}">
									<c:set value="${file.thumbnail.fileName}" var="thumb_file" />
									<img src="/upload/thumbnail/${thumb_file}">
								</c:if>
								
								${file.fileName} (사이즈:${file.fileSize})
								<input type="button" value="삭제" onclick="fileDel('${file.no}', '${file.saveFileName}', '${file.filePath}', '${thumb_file}')">
								
							</div>
							
						</c:forEach>
					</c:when>
					
				</c:choose>
				
				
				<div class="listbt">
					<a href="boardPage.jsp" class="on">목록</a>
					<input type="submit" value="글수정">
				</div>
			</form>
		</div>
	</div>

<script>
function fileDel(no, saveFileName, filePath, thumb_filename) {
	var ans = confirm("정말로 삭제하시겠습니까?");
	if (ans) {
		var x = new XMLHttpRequest();
		x.onreadystatechange = function() {
			if(x.readyState ===4 && x.status === 200) {
				var tag = document.getElementById("fileSector");
				
				if(x.responsText.trim() === "0") {
					alert("파일 삭제 실패");
				}	else {
					aler("파일 삭제함");
					tag.innerHTML = "<input type='file' name+'filename'>";
				}
					
			}	else {
					console.log('에러코드 : ' + x.status)
			}
		};
	}
	
		x.open("POST", "/file/fileDel", true);
		x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		x.send("no="+no+"&savefilename="+savefilename+"&filepath="+filepath+"&thumb_filename="+thumb_filename);
	
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