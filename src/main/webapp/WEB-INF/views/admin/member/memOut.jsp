<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>관리자 Main</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/admin.css">

</head>
<body>

	<%@ include file="../header.jsp"%>
	<%@ include file="../menu.jsp"%>

	
	<div class="contents" id="reserv">		 
    	<h1>회원관리</h1>
 	    
 	    <div>	
    	<form name="search" method="post" action="mOut.m">
    		<input type="text" name="odate" placeholder ="탈퇴요청 기준일 입력(yyyymmdd)"  value = "${odate}" > 
    		<input type="submit" value="검색">
    	</form>
    	
    	<button id="outMemDelBtn">탈퇴회원 일괄 삭제</button>
    	</div>
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
   				<td>${dto.grade}</td>
   				<td>${dto.wdate}</td>
   				<td><c:if test="${dto.odate != '9999/01/01'}">${dto.odate}</c:if></td>
   			</tr>   		
   		</c:forEach>
    				
    	</table>    	       
		
	</div>

	
	<%@ include file="../footer.jsp"%>
	
	<script>
	var outMemDelBtn = document.getElementById("outMemDelBtn");
	outMemDelBtn.addEventListener("click", function(){
		
		var odate = document.forms["search"]["odate"].value;
		if(odate.trim().length == 0){
			alert('탈퇴요청 기준일을 입력하세요');
			return;
		}
		
		var xhr = new XMLHttpRequest(); //ajax 객체 생성
		
		var params = 'odate='+odate;
		
		//let formData = new FormData(document.forms.search);
		//formData.append("middle", "Lee");
		
		
		xhr.onreadystatechange = function(){	
			
			if(xhr.readyState === 4){
				if(xhr.status === 200){
					var result = xhr.responseText.trim();
					console.log(result);
					if(result == "ok"){
						location.reload();
						alert('기준일로 검색된 탈퇴 요청한 회원이 삭제 되었습니다. ');
						//document.forms["search"]["odate"].value = null;
					} 
				} else {
					console.log('서버 에러(403, 404)');
				}
			}
		};
	
		var url = "mDel.m";
		xhr.open("post", url, true);
		xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); 
		xhr.send(params);
		
	}, true);
	

	</script>
	
</body>
</html>
