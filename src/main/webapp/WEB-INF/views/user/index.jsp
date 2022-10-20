<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- <%@ page import="common.*" %> --%>
<!DOCTYPE html>
<html>
<head>
<%@ include file = "common.jsp" %>
	<title>희주니 홈페이지</title>
</head>

	<script>
		function init() {
			// var msg = document.getElementsByName("msg").value; // ElementsByName 은 배열로 넘어옴
			var msg = document.getElementsByName("msg");
			//alert(msg);
			var alert_msg;
			var modal_pop = false;
			if(msg[0].value == "loginOk") {
				//alert("로그인이 되었습니다.");
				alert_msg = "로그인이 되었습니다.";
				modal_pop = false;
			}
			
			if(msg[0].value == "loginFail") {
				alert_msg = "로그인이 정보가 없습니다.";
				modal_pop = true;
				//var stat = document.getElementsByName("stat");
				//if(stat[0].value == 1) {
					// alert(stat[0].value);
			}
				
			if(msg[0].value == "memberOk") {
				alert_msg = "회원등록이 되었습니다.";
				modal_pop = true;
					//document.getElementById("modal").style.display ="block";
			}
			if(msg[0].value != "null") alert(alert_msg);
			if(modal_pop) document.getElementById("modal").style.display ="block";
		}
		
	</script>
	
<body onload="init()">
	<input type="text" name="msg" id="msg" value="<%= request.getAttribute("msg") %>">
</body>
<%@ include file="header.jsp" %>

<%@ include file="menu.jsp" %>

<%-- <c:set value="${loginUser}" var="loginuser" />
<p> 현재접속자수 : ${loginuser.getTotal_user()}명</p>

jsp코드 : <%= LoginImpl.total_user %> --%>

<div class="row">
  <div class="leftcolumn">
    <div class="card">
      <h2>The best place for Traveling</h2>
      <h5>blah blah blah, Dec 7, 2017</h5>
      <div class="fakeimg" style="height: 200px;">Image</div>
      <p>Some text..</p>
      <p>Some text..</p>
    </div>
    <div class="card">
      <h2>TITLE HEADING</h2>
      <h5>Title description, Sep 2, 2017</h5>
      <div class="fakeimg" style="height: 200px;">Image</div>
      <p>Some text..</p>
      <p>Some text..</p>
    </div>
  </div>
  <div class="rightcolumn">
    <div class="card">
      <h2>About Me</h2>
      <div class="fakeimg" style="height: 100px;">Image</div>
      <p>Some text..</p>
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

<%@ include file="footer.jsp" %>

<%@ include file="member/login_modal.jsp" %>

</body>
</html>