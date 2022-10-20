package com.cosmos.service;

import java.util.List;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.cosmos.dto.AttachFile;
import com.cosmos.dto.Board;
import com.cosmos.dto.Criteria;

public interface BoardService {
	
	public List<Board> list(Criteria cri);
	
	public int getTotalRec(Criteria criteria);
	
	public Board detailBoard(String seqno);
	
	//public String insertBoard(HttpServletRequest req, HttpServletResponse resp);
	
	public String insertBoard(Board board, MultipartFile files);
	
	public String update(HttpServletRequest req, HttpServletResponse resp);
	
	public void delete(String seqno);

}
