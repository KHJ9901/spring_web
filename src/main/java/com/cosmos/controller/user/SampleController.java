package com.cosmos.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cosmos.dto.Board;
import com.cosmos.dto.Member;

@Controller
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	@RequestMapping("doA")
	public ModelAndView doo() {
		logger.info("doA called..");
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("/member/memRegForm");
		mv.addObject("msg", "회원가입폼");
		
		return mv;
		
	}
	
	//리턴타입 : String인 경우
	//@ModelAttribute는 자동으로 해당 객체를 뷰까지 전달
	@RequestMapping("doB")
	public String doB(@ModelAttribute("msg") String message,  Model model) {
		logger.info("doB called..");
		logger.info("doB called.. {}",  message);
		
		Member m = new Member();
		m.setId("joy");
		m.setName("홍길동");
		
		Board b = new Board();
		b.setTitle("안녕자바");
		
		model.addAttribute("member", m);
		model.addAttribute(b);
		//model.addAttribute("msg","곧 점심시간");
		return "doC";
	}
	
	@RequestMapping("doC")
	public String doC(RedirectAttributes rttr) {
		
		Member m = new Member();
		m.setId("joy");
		m.setName("홍길동");
		
		Board b = new Board();
		b.setTitle("안녕자바");
		
		rttr.addFlashAttribute(m);
		rttr.addFlashAttribute(b);
		
		return "redirect:/doA";
	}
	
	//json 라이브러리 추가
	@RequestMapping("doJson")
	public @ResponseBody Member dojson() {
		Member m = new Member();
		m.setId("joy");
		m.setHobby_str("테니스, 골프");
		m.setName("홍길동");
		
		return m;
	}
	
 	
}







