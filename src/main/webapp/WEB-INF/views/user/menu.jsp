<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="${loginUser}" var="loginuser" />

<div class="topnav">
  <a href="/">홈</a>
  <a href="/board/list">게시판</a>
  <a href="#">참고문헌</a>
  <a href="#">즐겨찾기</a>

	<c:if test="${loginuser != null}" >
		<a class="chat" href="/chat">1:1채팅</a>
		<a class="chat" href="/chatList">채팅리스트</a>
		<font>${loginuser.name}님 반갑습니다!</font>
		<a href="/mypage.jsp" style="float:right">마이페이지</a>
		<a href="/member/logOut" style="float:right">로그아웃</a>
	</c:if>
	
	<c:if test="${loginuser eq null}" >
		<a href="javascript:loginTag()" style="float:right">로그인</a>
	</c:if>
</div>