package com.cosmos.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cosmos.dao.MemberDaoImp;
import com.cosmos.dto.*;
import com.cosmos.service.*;

@Controller
@RequestMapping("/member/")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//MemberService ms = new MemberServiceImp(mdo);
	
	@Autowired
	private MemberService ms;
	
	MemberController(MemberService ms) {
		this.ms = ms;
	}
	
	@RequestMapping("memRegForm")
	public void memRegForm() {
		logger.info("회원가입폼 매핑");
	}
	
	//@RequestMapping(value="register", method=RequestMethod.POST)
	@PostMapping("register")
	public String register(Member member) {
		
		  logger.info("회원가입처리 매핑");
		  
		  logger.info("아이디 :{}", member.getId());
		  logger.info("이름 :{}", member.getName());
		  
		  ms.insert(member);
		
		return "redirect:/";
	}
	
	//@RequestMapping(value="idDoubleCheck", method=RequestMethod.GET)
	@GetMapping("idDoubleCheck")
	public ResponseEntity<String> idDoubleCheck(@RequestParam("id") String id) {
		logger.info("idDoubleCheck called..");
		String rs = Integer.toString(ms.idDoubleCheck(id));
		
		return new ResponseEntity<String>(rs, HttpStatus.OK);
	
	}
	
	//로그아웃
	@RequestMapping(value="logOut", method = RequestMethod.GET)
	public String logoutMainGET(HttpServletRequest req) throws Exception{
		HttpSession session = req.getSession();
		session.invalidate();

		return "redirect:/"; 
	}
}
