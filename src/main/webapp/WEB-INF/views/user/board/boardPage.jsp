<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
	<div class="bdbox">
		<div class="bdtitle">
			<strong>게시판</strong>
			<p> 쾌적한 게시판 환경을 위해 타인에게 혐오감을 주는 표현은 삼가세요!</p>
		
			<c:if test="${loginuser != null }">
				<div class="wtbtcls">
					<a href="/board/regForm"> 등록 </a>
				</div>
			</c:if>
			
			<div class="search">
				<form name="search" method="post" action="/board/list">
					<input type="hidden" name="currentPage" value="${pageMaker.cri.currentPage}" />
					
					<select name="searchField">
					
						<option value="title" 
							<c:if test="${pageMaker.cri.searchField == 'title'}">selected</c:if>>제목
						</option>
						
						<option value="name"
							<c:if test="${pageMaker.cri.searchField == 'name'}">selected</c:if>>이름
						</option>
						
					</select>
					
					<input class="searchText" type="text" name="searchText" placeholder="검색하세요" 
						value="${pageMaker.cri.searchText}">
					<input type="button" value="검색" onclick="document.forms['search'].submit()">
					<!--  페이지당 레코드수  -->
					<select name="rowPerPage" onchange="goAction()">
					
						<c:forEach var="i" begin="5" end="40" step="5">
						
							<option value="${i}"
								<c:if test="${i == pageMaker.cri.rowPerPage}"> 
									selected
								</c:if>
							>${i}개씩
							</option>
							
						</c:forEach>
						
					</select>
					
				</form>
			</div>
		</div>
		
<script>
	function goAction() {
		document.forms["search"].submit();
	}
</script>
	
	<div class="bdbody">
		<div class="bdlist">
				<div class="top">
					<div class="num">번호</div>
					<div class="title">제목</div>
					<div class="date">작성일</div>
					<div class="count">조회수</div>
					<div class="name">작성자</div>
				</div>
				
				<c:forEach items="${board}" var="board">
				<div class="boardBar" onclick="location.href='/board/detail?seqno=${board.seqno}'">
					<div class="num">${board.no}</div>
					<div class="title" id="post">${board.title}</div>
					<div class="date">${board.wdate}</div>
					<div class="count">${board.count}</div>
					<div class="name">${board.name}</div>
				</div>
				</c:forEach>

<!-- 			<div id="b_modal">
			   <div id="board_modal">
			      <div id="contain">
			         <h2 id="board-title"></h2>
			         <a href="javascript:clos()" style="float:right">닫기</a>
			         <hr>
			         <p id="board-content"></p>
			      </div>
			   </div>
			</div> -->
	
		</div>
		
		<p>총레코드 개수 : ${pageMaker.total}</p>
		
		<div class="pagination">
			<c:if test="${pageMaker.prev}">
				<a href="/board/list?currentPage=${pageMaker.startPage-1}&rowPerPage=${pageMaker.cri.rowPerPage}">&laquo;</a>
			</c:if>
			
			<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
				<a href="/board/list?currentPage=${num}&rowPerPage=${pageMaker.cri.rowPerPage}"
				   class="${pageMaker.cri.currentPage == num ? "active=" : "" }">${num}</a>
			</c:forEach>
			
			
			<c:if test="${pageMaker.next}">
				<a href="/board/list?currentPage=${pageMaker.endPage+1}&rowPerPage=${pageMaker.cri.rowPerPage}">&raquo;</a>
			</c:if>
		</div>

	</div>	

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

<script>

//게시판 팝업창
function board_con(title, content){
   document.getElementById("b_modal").style.display = "block";
   document.getElementById("board-title").innerHTML = title;
   document.getElementById("board-content").innerHTML = content;
}
   function clos() {
   document.getElementById("b_modal").style.display = "none";
}
   

</script>

</body>
</html>



