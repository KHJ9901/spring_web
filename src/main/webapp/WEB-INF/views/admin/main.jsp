<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>관리자 Main</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/admin.css">

</head>
<body>

	<%@ include file="header.jsp"%>
	<%@ include file="menu.jsp"%>

	<!-- 통계정보 -->
	<div class="contents" id="reserv">
		<p>▣ 회원등록건수 : ${cur_cnt}</p>
		<p>▣ 회원탈최 요청 건수 : ${del_cnt}</p>
		
	</div>

	
	<%@ include file="footer.jsp"%>
	
</body>
</html>
