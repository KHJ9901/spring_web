package com.cosmos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cosmos.common.OracleConn;
import com.cosmos.dto.Member;

import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class MemberDaoImp implements MemberDao {
	
	@Autowired
	private DataSource ds;
	
	// private final Connection conn = OracleConn.getInstance().getConn();	
	
	@Override
	public Map<String, String> loginProc(String id, String pw) {				
		//String[] status = new String[2];
		
		PreparedStatement stmt = null;
		Connection conn = null;
		
		Map<String, String> map = new HashMap<String, String>();
		
		String sql = "select * from member where id = ?";
		try {
			conn = ds.getConnection();
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
			resourceClose(conn);
		}

		return map;
	}

	//자원반납
	private void resourceClose(Connection conn) {
		try {
			//stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//자원반납
	private void resourceClose(Connection conn, PreparedStatement stmt) {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int insertMember(Member member) {
		
		//System.out.println("취미 : " + member.getHobby()[0]);
		
		String email = member.getEid() + "@" + member.getDomain();
		
		Connection conn = null;
		CallableStatement stmt = null;

		int rs = 0;
		try {			
			conn = ds.getConnection();
			String sql = "call p_insert_member(?,?,?)"; 
			stmt = conn.prepareCall(sql);
			
			StructDescriptor st_desc =StructDescriptor.createDescriptor("OBJ_MEMBER", conn);
			Object[] obj_member = {member.getId(),
								   member.getPw(), 
								   member.getName(), 
								   member.getGender(), 
								   email,
								   member.getIntro()}; 
			
			STRUCT member_rec = new STRUCT(st_desc, conn, obj_member); 
			stmt.setObject(1, member_rec);
			
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor("STRING_NT", conn);
			ARRAY hobby_arr = new ARRAY(desc, conn, member.getHobby());
			stmt.setArray(2, hobby_arr);
			
			stmt.registerOutParameter(3, OracleTypes.INTEGER);
			stmt.executeUpdate();
			rs = stmt.getInt(3);
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			resourceClose(conn, stmt);
		}
				
		return rs;
	}

	@Override
	public int selectByid(String id) {
		Connection conn = null;
		CallableStatement stmt = null;
		
		int rs = 0;
        String sql = "call p_idDoubleCheck(?,?)";  		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareCall(sql);
			stmt.setString(1, id);
			stmt.registerOutParameter(2, OracleTypes.INTEGER);
			stmt.executeQuery();
			
			rs = stmt.getInt(2);
		} catch (SQLException e) {			
			e.printStackTrace();
		}	finally {
			resourceClose(conn, stmt);
		}
        
		return rs;
	}

	@Override
	public List<Member> getMember() {
		Connection conn = null;
		CallableStatement stmt = null;
		
		List<Member> member = new ArrayList<Member>();
		
		String sql ="call p_get_member(?)";
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(1);
			while(rs.next()) {
				Member m = new Member();
				m.setId(rs.getString("id"));
				m.setName(rs.getString("name"));
				m.setGender(rs.getString("gender"));
				m.setWdate(rs.getString("wdate"));
				
				//취미
				if(rs.getArray("hobby_nm") != null) {
					//컬렉션 중첩테이블 데이터 가져오기
					ARRAY h_arr = ((OracleResultSet)rs).getARRAY("hobby_nm");
					System.out.println("취미 타입:" + h_arr.getSQLTypeName());
					System.out.println("취미 타입코드:" + h_arr.getBaseType());
					System.out.println("취미 갯수:" + h_arr.length());
					
					String[] h_val = (String[])h_arr.getArray();
					for(int i=0; i < h_val.length; i++) {
						String hobby_str = h_val[i];
						System.out.println(">>>취미["+i+"]=" + hobby_str);
					}
					m.setHobby_str(Arrays.toString(h_val));					
				}
				
				member.add(m);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}	finally {
			resourceClose(conn, stmt);
		}
		
		return member;
	}
		
}









