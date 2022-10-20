package com.cosmos.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cosmos.dto.Member;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
								   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class MemberControllerTest {

	private static final Logger log = LoggerFactory.getLogger("MemberControllerTest.class");
	
	@Autowired
	private WebApplicationContext wac; //context 역할
	
	private MockMvc mockMvc; //요청, 응답 처리 , 서블릿 역할
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		log.info("mockMvc setup...");
	}
	
	//Controller
	@Test
	public void test() { //메소드 이름으로 객체가 만들어짐
		
		try {
			String rs = mockMvc.perform(MockMvcRequestBuilders.post("/login")
						.param("id", "khj1234").param("pw", "abcd1234")
						).andReturn().getModelAndView().getViewName();
			log.info("쳐찍어: " + rs);
			//ModelAndView 은값집어 넣고 페이지 설정 할 때
			//model 은 값만 가져옴
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//RestController
	@Test
	public void test2() throws Exception {
		Member member = new Member();
		member.setId("khj");
		member.setPw("abcd1234");
		member.setName("김희준");
		member.setGender("M");
		
		String jsonStr = new Gson().toJson(member);
		log.info(jsonStr);
		mockMvc.perform(MockMvcRequestBuilders.post("/ex/member")
						.contentType(MediaType.APPLICATION_JSON)
						.contentType(jsonStr)).andExpect(status().is(200));
	}

}
