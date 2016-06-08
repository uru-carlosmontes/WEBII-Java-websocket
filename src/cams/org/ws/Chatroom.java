package cams.org.ws;

import javax.websocket.OnOpen;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/chat")
public class Chatroom {
	private static Set<Session> clients = 
			Collections.synchronizedSet(new HashSet<Session>());
	
	
	@OnMessage
	public void onMessage (String msg, Session session)
			throws IOException{
		
		synchronized (clients) {
			for (Session client : clients) {
				client.getBasicRemote().sendText(msg);
			}
		}
		
	}
	
	@OnOpen
	public void onOpen(Session session) {
		clients.add(session);
	}
	
	@OnClose
	public void onClose(Session session) {
		clients.remove(session);
	}
	

}
