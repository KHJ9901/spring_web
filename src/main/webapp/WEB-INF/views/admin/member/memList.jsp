<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>관리자 Main</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/admin.css">

</head>
<body>

	<%@ include file="../header.jsp"%>
	<%@ include file="../menu.jsp"%>

	
	<div class="contents" id="reserv">		 
    	<h1>회원관리</h1>
 	    	
    	<form name="search" method="post" action="mList.m">
    		<input type="radio" name="grade" value="1" <c:if test=""> checked </c:if>> 일반회원
    		<input type="radio" name="grade" value="2" <c:if test=""> checked </c:if>> 운영자
    		<input type="radio" name="grade" value="3" <c:if test=""> checked </c:if>> 관리자
    		<input type="text" name="name" placeholder ="이름을 입력하세요"  value = "${name}" > 
    		<input type="submit" value="검색">
    	</form>
    	
    	인원수 : ${fn:length(list)}명
    	<table border="1" cellspacing="0">
    		<tr>
    			<th>순번</th>
    			<th>id</th>
    			<th>이름</th>
    			<th>성별</th>    			
    			<th>등급</th>
    			<th>가입일자</th>    			
    			<th>탈퇴일자</th>
    		</tr>	   
    		
   		<c:forEach items="${list}" var="dto">
   			<tr>
   				<td>${dto.num}</td>
   				<td>${dto.id}</td>
   				<td>${dto.name}</td>
   				<td>${dto.gender}</td>
   				<td></td>
   				<td>${dto.wdate}</td>
   				<td><c:if test="${dto.odate != '9999/01/01'}">${dto.odate}</c:if></td>
   			</tr>   		
   		</c:forEach>
    				
    	</table>    	       
		
	</div>

	
	<%@ include file="../footer.jsp"%>
	
</body>
</html>
