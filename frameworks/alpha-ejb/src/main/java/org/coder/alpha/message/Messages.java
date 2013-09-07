/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Messages.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Messages {
	
	/**
	 * max error level.
	 */
	private int maxLevel = MessageLevel.INFO.ordinal();

	/**
	 * the message list.
	 */
	private List<Message> messageList = new ArrayList<Message>();
	
	/**
	 * @param message to add
	 * @return self
	 */
	public Messages add(Message message){
		if(maxLevel < message.getMessageLevel()){
			maxLevel = message.getMessageLevel();
		}
		this.messageList.add(message);
		return this;
	}
	
	/**
	 * @return message list
	 */
	public List<Message> getMessageList(){
		return messageList;
	}
	
	/**
	 * @return max level
	 */
	public MessageLevel getMaxLevel(){
		return MessageLevel.valueOf(maxLevel);
	}
}
