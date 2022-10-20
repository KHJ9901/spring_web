package com.cosmos.controller.user;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cosmos.common.LoginImpl;
import com.cosmos.dao.MemberDao;

@Controller
public class Login {
	private static final Logger log = LoggerFactory.getLogger(Login.class);
       
	@Autowired
	MemberDao dao;
	
	@PostMapping("/login")
	//public String login(Member member) {	
	public String login(@RequestParam("id") String id, //--> 너무 길면 Member member 로
						@RequestParam("pw") String pw,
						HttpSession sess,
						Model model) {	
		Map<String, String> map = dao.loginProc(id, pw);
		String viewPage = null;
		
		switch(map.get("login")) {				
		case "ok" : 
			//세션설정
			LoginImpl loginUser = new LoginImpl(id, map.get("name"));	
			sess.setAttribute("loginUser", loginUser);
			model. addAttribute("msg", "loginOk");		
			viewPage = "redirect:/";
			break;				
		default : //로그인 실패
			model.addAttribute("msg", "loginFail");
			viewPage = "index";
		}	
//--------------------------------user---------------------------------------	
		return viewPage;
		
	}

}







