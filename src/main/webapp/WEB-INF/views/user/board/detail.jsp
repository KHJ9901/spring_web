<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="../common.jsp" %>
	<title>희주니 게시판</title>
</head>

<body>

<%@ include file="../header.jsp" %>

<%@ include file="../menu.jsp" %>

<div class="row">
  <div class="leftcolumn">
    <!-- <div class="card"> -->
	<div class="bdbox">
		<div class="bdtitle">
		
		<c:set value="${board}" var="board" />
			<strong>${board.seqno}번째 글</strong>
			<p> 쾌적한 게시판 환경을 위해 타인에게 혐오감을 주는 표현은 삼가세요!</p>
		</div>
		
	<div class="bdbody">
	
		<table>
			<tr>
				<td colspan="3"> <label>제목 : ${board.title}</label></td> 
			</tr>
			
			<tr>
				<td> <label>작성일자 : ${board.wdate}</label><br> </td>
				<td> <label>작성자 : ${board.name}</label><br> </td>
				<td> <label>조회수 : ${board.count}</label><br> </td>
			</tr>
			
			<tr>
				<td colspan="3"><label>내용 : ${board.content}</label><br></td>
			</tr>
		</table><br> 
		
		
		<!-- 첨부파일 -->
		<div>
		<c:set value="${board.attachfile}" var="attachfile" />
		<c:if test="${attachfile != null}">
			<c:forEach items="${attachfile}" var="file">
			
				<form name="filedown" method="post" action="/file/fileDown">
					<input type="hidden" name="filename" value="${file.fileName}">
					<input type="hidden" name="savefilename" value="${file.saveFileName}">
					<input type="hidden" name="filepath" value="${file.filePath}">
					
					<c:set value="${file.fileType}" var="filetype" />
					<c:set value="${fn:substring(filetype, 0, fn:indexOf(filetype, '/')) }" var="type" />
					
					<c:if test="${type eq 'image'}">
						<c:set value="${file.thumbnail.fileName}" var="thumb_file" />
						<img src="/upload/thumbnail/${thumb_file}">
					</c:if>
					
					${file.fileName} (사이즈: ${file.fileSize} bytes)
					<input type="submit" value="다운로드">
					
				</form>
			</c:forEach>
		</c:if>
		</div>
		
		<!-- 댓글 등록 폼  -->
		<div>
			<textarea id="comment" name="comment" placeholder="댓글작성" rows="2" cols="50"></textarea>
			<button id="addReplyBtn">댓글등록</button>
		</div>
		
		<hr>
		
		<!-- 댓글 리스트 출력 영역 -->
		<div id="reply-list">
			<ul class="reply_ul">
				<li data-rno='8'>
					<div>작성자 | 작성일자 | 댓글내용</div>
				</li>
			</ul>
		</div>
		
		<!-- 댓글 페이지 리스트 출력  -->
		<div class="reply-page-list">
		
		</div>
		
		
		<div class="reply">
			<c:set value="${board.reply}" var="reply" /> 
			<c:if test="${reply != null}">
				<c:forEach items="${reply}" var="r">
					${r.comment}
					${r.name}
					${r.wdate}
					<hr>
				</c:forEach>
			</c:if>
		</div>
		
		<div class="listbt">
		
			<a href="/khjweb/board/boardPage.jsp" class="on">목록</a>
			
			<c:set value="${board}" var="board" />
			<c:set value="${loginUser}" var="user" />
			
			<c:if test="${user.id eq board.id}">
				<a href="detail.bo?seqno=${board.seqno}&page=modify">수정</a>
				<a href="javascript:del_confirm('${board.seqno}')">삭제</a>
			</c:if>
			
		</div>
	</div>	
	<script>
	function del_confirm(seqno) {
		var rs = confirm('정말로 삭제하시겠습니까?');
		if(rs) {
			location.href="boardDelete?seqno=" + seqno;
		}
	}
	</script>	

      <h5>Title description, Sep 2, 2017</h5>
      <div class="fakeimg" style="height:200px;">Image</div>
      <p>Some text..</p>
      <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
    </div>
  </div>
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

<!-- 댓글 모달  -->
<div id="reply_modal">
	<div class="modal-content">
		<div class="login">
			<button id="modalCloseBtn">
				<img id="나가기" src="https://cdn-icons-png.flaticon.com/512/458/458594.png">
			</button>

			<textarea name="content"> </textarea>
			
			<button id="replyModifyBtn">댓글수정</button>
			<button id="replyDeleteBtn">댓글삭제</button>
		</div>
	</div>
</div>

<script type="text/javascript" src="/js/reply.js"></script>
<script>

//즉시실행함수(IIFE)
//(function() {
//	문장;
//})(); 
var seqno = '<c:out value="${board.seqno}" />';
var id = '<c:out value="${user.id}" />';

$(document).ready(function(){
	console.log(replyService);
	
	console.log("==================구분선===================");
	console.log("Reply get LIST");
	
	//댓글 모달창
	var modal = $("#reply_modal");
	var modal_content = modal.find("textarea[name='content']");
	
	modal.hide();
	
	showList(1);
	
	var currentPage = 1;
	
	/* 댓글 목록  */
	function showList(page) {
		replyService.getList({bno:seqno, page:page || 1}, function(replyCnt, list) {
//			for(var i=0, len=list.length || 0; i < len; i++) {
//				console.log(list[i]);
//			}
			console.log("댓글 수 : " + replyCnt);
			/* 댓글이 새롭게 등록 된 경우 */
			if(page == -1) {
				currentPage = Math.ceil(replyCnt/5.0);
				showList(currentPage);
				return;
			}
			
			/* 댓글이 없는 경우 */
			if(list == null || list.length == 0) {
				$(".reply_ul").html("");
				return;
			}
			
			console.log("==================구분선===================");
			/* 댓글이 있는 경우 */
			var str="";
			for(var i=0, len=list.length || 0; i < len; i++) {
				console.log(list[i]);
				str += "<li data-rno='" + list[i].seqno + "'><div class='replyRow'>" + list[i].rn + " | " + list[i].id ;
				str += " | " + list[i].wdate + " | " + list[i].content + "</div></li>";
			}
			$(".reply_ul").html(str);
			
			showReplyPage(replyCnt, currentPage);
		});
	}
	
	//showReplyPage(10);
	
	// 댓글 페이지 리스트 출력
	function showReplyPage(replyCnt) {
		
		//var currentPage = 1;
		
		var endPage = Math.ceil(currentPage/5.0)*5; 
		//무조건 윗번호로 반올림함
		
		var startPage = endPage - 4;
		console.log("endPage : " + endPage);
		
		//startNum 과 endNum 은 페이지의 값 인덱스 번호
		var prev = startPage != 1;
		var next = false;
		
		if(endPage * 5 >= replyCnt) {
			endPage = Math.ceil(replyCnt/5.0);
		}
		if(endPage * 5 < replyCnt) {
			next = true;
		}
		
		var str = "<ul class='pageUl' style='display: -webkit-inline-box; letter-spacing: 20px;'>";
		if(prev) {
			str += "<li class='page-link'>";
			str += "<a href='" + (startPage - 1)+"'> 이전페이지 </a><li>";
		}
		
		for(var i=startPage; i <= endPage; i++){
			var active = currentPage == i ? "active" : "";
			str += "<li class='page-link " + active + "'>";
			str += "<a href='" + i + "'>" + i + "</a></li>";
		}
		
		if(next) {
			str += "<li class='page-link'>";
			str += "<a href='" + (endPage + 1) + "'> 다음페이지 </a></li>";
		}
		
		str += "</ul>";
		console.log(str);
		$(".reply-page-list").html(str);
	}
	
	/* 댓글 페이지번호 클릭 시 */
	$(".reply-page-list").on("click", "li a", function(e){
		console.log("페이지 번호 클릭=================================");
		e.preventDefault(); //a테그를 눌러도 href 링크로 이동하지 않게
		var clickPage = $(this).attr("href");
		console.log("currentPage : " + clickPage);
		currentPage = clickPage;
		showList(currentPage);
	});

	
	/* 모달 닫기 버튼  */
	$("#modalCloseBtn").on("click", function(e){
		modal.hide();	
	});
	
	$(".reply_ul").on("click", "li", function(e){
		var rno = $(this).data("rno");
		replyService.getOneReply(rno, function(data){
			console.log("==================REPLY GET DATA==================");
			console.log("댓글"+rno+"번 내용 : " + data.content);
			modal_content.val(data.content);
			modal.data("rno", data.seqno); //input hidden 같은 역할
		});
		modal.show();
	
	});
	
	/* 댓글 삽입 */
	$("#addReplyBtn").on("click", function(e) {
		var comment = document.getElementById("comment").value;
		
		var reply = {
			boardNo : seqno,
			id : id,
			comment : comment
		};
		
		replyService.add(reply, function(result){
			alert("댓글이 등록되었습니다." + result);
			document.getElementById("comment").value = "";
			showList(-1);
			//document.getElementById("newLine").innerHTML = "<li>" + reply.comment + "</li>";
		});	
	});
	
	/* 댓글 수정   */
	$("#replyModifyBtn").on("click", function(e){
		console.log("댓글 수정 번호 : " + modal.data("rno"));
		console.log("댓글 수정 내용 : " + modal_content.val());
		
		var reply = {seqno : modal.data("rno"), 
					 content : modal_content.val()};
		
		replyService.updateReply(reply, function(result){
			alert(result);
			modal.hide();
			showList(currentPage);
		});
	});
	
	/* 댓글 삭제  */
	$("#replyDeleteBtn").on("click", function(e){
		var rno = modal.data("rno");
		console.log("댓글 삭제 번호 : " + rno);
		
		replyService.deleteReply(rno, function(result){
			alert(result);
			modal.hide();
			showList(currentPage);
		});
	});
});
</script>


</body>
</html>



