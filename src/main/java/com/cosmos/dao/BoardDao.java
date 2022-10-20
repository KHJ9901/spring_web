package com.cosmos.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.cosmos.dto.AttachFile;
import com.cosmos.dto.Board;
import com.cosmos.dto.Criteria;

public interface BoardDao {
	
	public List<Board> boardList(Criteria cri);
	
	public Board boardDetail(String seqno);
	
	public String insert(Board board, AttachFile attach);
	
	public void insert(Board board);
	
	void insertThumbNail(String attach_no, AttachFile attachFile);
	
	String insertAttachFile(String seqno, AttachFile attachFile);
	
	public void update(Board board, AttachFile attachFile);
	
	public int getTotalRec(Criteria cri);
	
	public Map<String, String> deleteByNo(String seqno);

	
	
}
