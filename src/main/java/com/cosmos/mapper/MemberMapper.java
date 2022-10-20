package com.cosmos.mapper;

import com.cosmos.dto.Member;

public interface MemberMapper {
	
	public Member getById(String id); //MemberMapper.xml 에 있는 select id
	
}
