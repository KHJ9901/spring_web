package com.cosmos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cosmos.dto.Criteria;
import com.cosmos.dto.Reply;
import com.cosmos.dto.ReplyVO;

public interface ReplyMapper {


	public List<ReplyVO> getList( //매개변수가 1개 이상일때 param으로 지정 해줘야
								@Param("cri") Criteria cri, 
								@Param("bno") Long bno);

	public ReplyVO getOne(Long rno);
	
	public int insert(Reply reply);

	public int update(ReplyVO vo);

	public int delete(Long rno);
	
	public int getCountByBno(Long bno);

}
