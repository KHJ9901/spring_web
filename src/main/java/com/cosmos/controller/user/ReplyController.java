package com.cosmos.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cosmos.dto.Criteria;
import com.cosmos.dto.Reply;
import com.cosmos.dto.ReplyPageDTO;
import com.cosmos.dto.ReplyVO;
import com.cosmos.service.ReplyService;

@RestController
@RequestMapping("/reply/")
public class ReplyController {

	private static final Logger log = LoggerFactory.getLogger(ReplyController.class);
	
	@Autowired
	ReplyService service;
	
	// 댓글 목록
	@GetMapping(value="list/{bno}/{page}",
				produces = {MediaType.APPLICATION_XML_VALUE,
							MediaType.APPLICATION_JSON_UTF8_VALUE
	})
	public ResponseEntity<ReplyPageDTO> getList(
										@PathVariable("bno") Long bno,
										@PathVariable("page") int page) {
		log.info("getList.........................................");
		Criteria cri = new Criteria(page, 5);
		
		return new ResponseEntity<>(service.getListPage(cri, bno), HttpStatus.OK);
	}
	
	// 댓글 디테일
	@GetMapping(value="{rno}",
				produces = {MediaType.APPLICATION_XML_VALUE,
							MediaType.APPLICATION_JSON_UTF8_VALUE})
	
	public ResponseEntity<ReplyVO> getOneReply(
						  @PathVariable("rno") Long rno) {
		log.info("getOneReply" + rno + "....................................");

		return new ResponseEntity<>(service.getOneReply(rno), HttpStatus.OK);
		
	}
	
	// 댓글 삽입
	@PostMapping(value="add",
			consumes = "application/json",
			produces = "text/plain; charset=utf-8") // 헤더에 포함하는 타입
	public ResponseEntity<String> create(@RequestBody Reply reply) {
		
		log.info("ReplyController create() has been called.. " + reply);
		int rs = service.register(reply); // 인서트 성공하면 rs = 1, 실패하면 rs = 0
		
		return rs == 1 ? new ResponseEntity<>("성공", HttpStatus.OK) 	// rs가 1이면 ? 생성해라 : 아니면
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 댓글 수정
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
							  value="{rno}",
							  produces = "text/plain; charset=utf-8")
	
	public ResponseEntity<String> modify(@PathVariable("rno") Long rno,
										 @RequestBody ReplyVO vo) {
		log.info("===================REST Controller modify() 호출======================");
		log.info("rno : " + vo.getSeqno());
		log.info("content : " + vo.getContent());
		return service.modify(vo) == 1 ? // true 일때 
				new ResponseEntity<>("댓글 수정 완료", HttpStatus.OK) : // 아니면
				new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 댓글 삭제
	@DeleteMapping(value="{rno}",
				   produces = "text/plain; charset=utf-8")
	
	public ResponseEntity<String> delete(@PathVariable("rno") Long rno) {
		log.info("===================REST Controller delete() 호출======================");
		log.info("삭제 rno : " + rno);
		return service.delete(rno) == 1 ? // true 일때 
				new ResponseEntity<>("댓글 삭제 완료", HttpStatus.OK) : // 아니면
					new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
