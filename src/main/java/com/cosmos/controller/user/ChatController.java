package com.cosmos.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

	@GetMapping("/chat")
	public String regForm() {
		return "chat/chat";
	}
	
	@GetMapping("/chatList")
	public String chatList() {
		return "chat/chatList";
	}
	
	@GetMapping("/chat2")
	public String chat2(String chatNo, Model model) { // 매개변수 이름이 같으면 바로 받아 올수 있음
		System.out.println("채팅방번호 : " + chatNo);
		model.addAttribute("chatNo", chatNo);
		return "chat/chat2";
	}
	
}
