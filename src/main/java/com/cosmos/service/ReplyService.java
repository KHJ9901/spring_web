package com.cosmos.service;

import java.util.List;

import com.cosmos.dto.Criteria;
import com.cosmos.dto.Reply;
import com.cosmos.dto.ReplyPageDTO;
import com.cosmos.dto.ReplyVO;

public interface ReplyService {
	
	
	public List<ReplyVO> getList(Criteria cri, Long bno);
	
	public ReplyPageDTO getListPage(Criteria cri, Long bno);
	
	public ReplyVO getOneReply(Long rno);
	
	public int register(Reply reply);

	public int modify(ReplyVO vo);

	public int delete(Long rno);
	
}
