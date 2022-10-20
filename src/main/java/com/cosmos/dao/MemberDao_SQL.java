package com.cosmos.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cosmos.common.OracleConn;

import oracle.jdbc.OracleTypes;


public class MemberDao_SQL {
	
	private final Connection conn = OracleConn.getInstance().getConn();	
	PreparedStatement stmt = null;
	
	
	
	public Map<String, String> loginProc(String id, String pw) {				
		//String[] status = new String[2];
		Map<String, String> map = new HashMap<String, String>();
		
		
		String sql = "select * from member where id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();			

			if(rs.next()) {	
				if(rs.getString("pw").equals(pw)){			
				   map.put("login", "ok"); //로그인 성공
				   map.put("name", rs.getString("name"));
				} else {
				   map.put("login", "pwFail");
				}
			} else {
				map.put("login", "fail");
			}
		} catch (SQLException  e) {			
			e.printStackTrace();
		} finally {
			//resourceClose();
		}

		return map;
	}

	//자원반납
	private void resourceClose() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public int insertMember(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");

		String[] hobby = request.getParameterValues("hobby");
		String hobby_str = new String();
		for(int i=0; i < hobby.length; i++){
			hobby_str += hobby[i];
			if(i == hobby.length-1){
				break;
			}
				hobby_str += ",";
		}

		String email = request.getParameter("eid") + "@" + request.getParameter("domain");
		String intro = request.getParameter("intro");

		Statement stmt;
		int rs = 0;
		try {
			stmt = conn.createStatement();
			/*
			String sql  = "INSERT INTO member(id, pw, name, gender, hobby, email, intro )";
			       sql += " VALUES ( '" + id + "','" + pw + "','" + name + "','" + gender; 
			       sql += "','" + hobby_str + "','" + email + "','" + intro + "')";
			*/

			String sql = String.format("INSERT INTO member" + 
					                  "(id, pw, name, gender, hobby, email, intro ) " + 
					                 " values('%s', '%s','%s','%s','%s','%s','%s')", 
					                 id, pw, name, gender, hobby_str, email, intro);

			System.out.println(sql);     
			
			//레코드 삽입, 수정, 삭제시 executeUpdate() 사용		
			rs = stmt.executeUpdate(sql);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
				
		return rs;
	}

	public int selectByid(String id) {
		CallableStatement stmt = null;
		int rs = 0;
        String sql = "call p_idDoubleCheck(?, ?)";  		
		try {
			stmt = conn.prepareCall(sql);
			stmt.setNString(1, id);
			stmt.registerOutParameter(2, OracleTypes.INTEGER);
			stmt.executeQuery();
			
			rs = stmt.getInt(2);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
        
		return rs;
	}
		
}









