/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.web;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * OrderBean.
 *
 * @author yoshida-n
 * @version	created.
 */
@ServerEndpoint("/endpoint")
public class OrderSocket{
	
	private Logger log = Logger.getLogger(getClass().getName());
	
	private static Set<Session> session = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnMessage
	public void onMessage(String message){
		for(Session s :session ){
			log.info("げっとー" +message);
			s.getAsyncRemote().sendText(message);
		}
	}
	
	@OnOpen
	public void open(Session sess){
		log.info("open socket");
		session.add(sess);
	}
	
	@OnClose
	public void close(Session sess){
		log.info("close socket");
		session.remove(sess);
	}
}
