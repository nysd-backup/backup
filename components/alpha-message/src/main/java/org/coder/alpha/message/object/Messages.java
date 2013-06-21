/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.message.object;

import java.util.ArrayList;
import java.util.List;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Messages {
	
	private int maxLevel = MessageLevel.INFO.ordinal();

	private List<Message> messageList = new ArrayList<Message>();
	
	public Messages add(Message message){
		if(maxLevel < message.getMessageLevel()){
			maxLevel = message.getMessageLevel();
		}
		this.messageList.add(message);
		return this;
	}
	
	public List<Message> getMessageList(){
		return messageList;
	}
	
	public MessageLevel getMaxLevel(){
		return MessageLevel.valueOf(maxLevel);
	}
}
