package com.cosmos.dao;

import java.util.List;
import java.util.Map;

import com.cosmos.dto.Member;

public interface MemberDao {

	public Map<String, String> loginProc(String id, String pw);
	
	public int insertMember(Member member);
	
	public int selectByid(String id);
	
	public List<Member> getMember();
	
}
