package com.cosmos.controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

@ServerEndpoint(value="/chatServer")
public class ChatServer {

	//연결된 모든 session 저장
	private static Map<String, Session> userMap = new HashMap<>();
	
	//방에 들어온 방번호, userid 저장
	private static Map<String, List<String>> chatUser = new HashMap<>();
	
	//클라이언트 연결 요청시
	@OnOpen
	public void open(Session session) {
		System.out.println("Open session id : " + session.getId() + ", session : " + session);
	}
	
	//클라이언트가 메세지 전송시
	@OnMessage
	public void handleMessage(Session session, String message) throws IOException {
		
		System.out.println("handleMessage : " + message);
		
		JSONObject obj = new JSONObject(message);
		String step = obj.getString("step");
		String chatNo = obj.getString("chatNo");
		String userid = obj.getString("userid");
		String msg = obj.getString("msg");
		String txt = null;
		
		//System.out.println("step : " + step + ", chatNo : " + chatNo + ", userid : " + userid + ", msg : " + msg);
		switch(step) {
			case "enter" : //클라이언트 대화방 입장
				userMap.put(userid,  session);
				if(chatUser.get(chatNo) == null) {
					List<String> list = new ArrayList<>();
					chatUser.put(chatNo, list);
				}
				chatUser.get(chatNo).add(userid);
				
				System.out.println("[" + userid + "] 입장~~~" );
				
				txt = "[" + userid + "] 입장 했습니다~~~~";
				
				break;
				
			case "chat" :
				txt = "[" + userid + "]" + msg; 
				
				break;
				
			case "out" :
				chatUser.get(chatNo).remove(userid);
				userMap.remove(userid);
				System.out.println(userid + "님 퇴장");
				txt = "[" + userid + "] 퇴장했습니다.";
				break;
		}
		
		//클라이언트에 메세지 전송(브로드캐스팅)
		List<String> chatUserList = chatUser.get(chatNo);
		for(int i=0; i < chatUserList.size(); i++) {
			System.out.println(chatNo + "번방에 들어온 ID 리스트================================");
			System.out.println("아이디 : " + chatUserList.get(i));
			System.out.println("세션 : " + userMap.get(chatUserList.get(i)).getId());
			System.out.println("=======================================================");
			
			userMap.get(chatUserList.get(i)).getBasicRemote().sendText(txt);
		}
	}
	
	//세션 연결 종료
	@OnClose
	public void onClose(Session session) {
		System.out.println("OnClose : " + session);
	}
	
	//에러 발생
	@OnError
	public void onError(Throwable e, Session session) {
		System.out.println("에러 발생 : " + e.getStackTrace());
	}
	
}
