package com.cosmos.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;

import com.cosmos.dto.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ReplyMapperTest {

	private static final Logger log = LoggerFactory.getLogger("ReplyMapperTest.class");
	
	@Autowired
	private ReplyMapper rm;
	

//	@Test
//	public void test() { //메소드 이름으로 객체가 만들어짐
//		
//		Reply r = new Reply();
//		r.setComment("안녕하세요");
//		r.setId("khj1234");
//		r.setBoardNo("35");
//		
//		rm.insert(r);
//	}
	
//	@Test
//	public void testList() {
//		
//		Criteria cri = new Criteria(1, 5);
//		List<ReplyVO> list = rm.getList(cri, 35L);
//		for(ReplyVO r : list) {
//			log.info("댓글내용: " + r.getContent());
//		}
//	}
	
	@Test
	public void testUpdate() {
		ReplyVO vo = new ReplyVO();
		vo.setSeqno(12L);
		vo.setContent("아작스 댓글 수정합니다");
		int count = rm.update(vo);
		log.info("update count : " + count);
	}

}
