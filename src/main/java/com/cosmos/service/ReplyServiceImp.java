package com.cosmos.service;

import org.slf4j.LoggerFactory;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmos.dto.*;
import com.cosmos.mapper.ReplyMapper;

@Service
public class ReplyServiceImp implements ReplyService {
	
	private static final Logger log = LoggerFactory.getLogger(ReplyServiceImp.class);
	
	@Autowired
	private ReplyMapper mapper;

	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		return mapper.getList(cri, bno);
	}
	
	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		return new ReplyPageDTO(mapper.getCountByBno(bno), 
								mapper.getList(cri, bno));
	}
	
	@Override
	public ReplyVO getOneReply(Long rno) {
		return mapper.getOne(rno);
	}
	
	@Override
	public int register(Reply reply) {
		log.info("reply register service called.." + reply);
		return mapper.insert(reply);
	}

	@Override
	public int modify(ReplyVO vo) {
		return mapper.update(vo);
	}

	@Override
	public int delete(Long rno) {
		return mapper.delete(rno);
	}



}
