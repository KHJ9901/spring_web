<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.cosmos.dto.*" %>
<%
	Member[] member = (Member[])request.getAttribute("member");
	String[] auth = (String[])request.getAttribute("auth");
	boolean chk1=false, chk2=false, chk3=false;
	if(auth != null ){
		//System.out.println("array size  = " + auth.length);
		int i=0;
		for(String a: auth) {
			if(a.equals("M")) chk1 = true;
			if(a.equals("U")) chk2 = true;
			if(a.equals("A")) chk3 = true;
			System.out.println("auth[" + i++ + "] = " + a);			
		}		
		
	}
	String name =(String)request.getAttribute("name");
	boolean listOutMember = (boolean)request.getAttribute("listOutMember");
%>    
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
			    
    	<form name="search" method="post" action="/m/mList.do">
    		<input type="checkbox" name="auth" value="M"
    		<% if(chk1) out.print(" checked"); %>> 일반회원
    		<input type="checkbox" name="auth" value="U"
    		<% if(chk2) out.print(" checked"); %>> 운영자
    		<input type="checkbox" name="auth" value="A"
    		<% if(chk3) out.print(" checked"); %>> 관리자
    		<input type="text" name="name" value="<%if(name != null) out.print(name); %>" placeholder ="이름을 입력하세요" > 
    		<input type="submit" value="검색">
    		<input type="button" value="탈퇴회원검색" onclick="location.href='/m/mOutList.do'">
    	 	<% if(listOutMember) { %>
    			<a href="/m/mOutDel.do">[탈퇴회원삭제]</a>
    		<%} %>
    
    	</form>   	
    	인원수 : <%= member.length %>명
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
    		
<%--    		<% for(Member m : member) { %>
   			<tr>
   				<td><%= m.getNum() %></td>
   				<td><%= m.getId()  %></td>
   				<td><%= m.getName() %></td>
   				<td><%= m.getGender() %></td>
   				  <td><%= m.getAuth_str() %></td>
   				<td><%= m.getWdate() %></td>
   				<td><%= m.getOdate() %></td>s
   			</tr>   		
   		<% } %> --%>
    				
    	</table>    	       
		
	</div>

	
	<%@ include file="../footer.jsp"%>
	
</body>
</html>
