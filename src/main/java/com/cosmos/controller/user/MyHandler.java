package com.cosmos.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyHandler extends TextWebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger(MyHandler.class);
	private List<WebSocketSession> users = new ArrayList<WebSocketSession>();
	private Map<String, WebSocketSession> userMap;
	
	public MyHandler() {
		userMap = new HashMap<String, WebSocketSession>(); // 아이디는 String, 아이디 세션은 웹소캣
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 웹소캣의 세션 정보 : 세션 정보를 던져줌
		System.out.println("TextWebSocketHandler: 연결생성, seesion : " + session);
		users.add(session);
	}

	@Override // 메세지를 받을려면 아래 메소드를 선언 해줘야
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println("TextWebSocketHandler : 메세지 수신, message : " + message.getPayload());
		
		String msg = (String) message.getPayload();
		JSONObject obj = new JSONObject(msg);
		
		
//		Iterator it = obj.keys();
//		while(it.hasNext()) {
//			String key = (String)it.next();
//			System.out.println("key : " + key + ", value : " + obj.getString(key));
//		}
		
		String type = obj.getString("type"); // 제이슨 형도태로 들어있는 obj 에서 타입가져오기
		if(obj != null && type.contentEquals("register")) {
			String user = obj.getString("userid");
			userMap.put(user, session); //userMap = new HashMap<String, Object>() 이라고 해놨으니까
			
		} else if(type.contentEquals("chat")) {
			String target = obj.getString("target");
			WebSocketSession ws = userMap.get(target);
			if(ws != null) {
				String sendMsg = "[" + target + "]" + obj.getString("message"); //채팅창에 채팅 내용 띄우기
				// 서버에 보냈다가 target id 에 송신함.
				ws.sendMessage(new TextMessage(sendMsg)); //채팅창에 채팅 내용 띄우기
				
			}
		}
		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
	}

	
}
