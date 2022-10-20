<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = "java.sql.*" %>

<%
//mysql db연결
//Class.forName("com.mysql.jdbc.Driver");
//String url = "jdbc:mysql://192.168.10.247/jjj"; // 너무 기니 따로 빼내기
//Connection conn = DriverManager.getConnection(url, "zzz", "Khj1031!");
//System.out.println("mysql 연결완료");

Class.forName("oracle.jdbc.driver.OracleDriver");
String url = "jdbc:oracle:thin:@192.168.10.247:1521:xe";
Connection conn = DriverManager.getConnection(url, "khj", "abcd");
System.out.println("워롸퀄 연결완료");
%>