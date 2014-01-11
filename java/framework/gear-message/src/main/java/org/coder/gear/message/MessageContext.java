/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.message;

import java.util.ArrayList;
import java.util.List;



/**
 * The thread-local context.
 *
 * @author	yoshida-n
 * @version	1.0
 */
public class MessageContext {
	
	private int maxLevel = MessageLevel.INFO.ordinal();
	
	/** the messages. */
	private List<Message> messages;
	
	private boolean rollbackOnly = false;
	
	private MessageHandler messageHandler = null;
	
	/** the thread local instance*/
	private static ThreadLocal<MessageContext> instance = new ThreadLocal<MessageContext>(){
		protected MessageContext initialValue() {
			return null;
		}
	};

	/**
	 * @param context the context to set
	 */
	protected static void setCurrentInstance(MessageContext context){
		if (context == null) {
			instance.remove();
		} else {
			instance.set(context);
		}
	}
	
	public void setMessageHandler(MessageHandler messageHandler){
		this.messageHandler = messageHandler;
	}

	/**
	 * @return the current context
	 */
	public static MessageContext getCurrentInstance(){
		return instance.get();
	}

	/**
	 * @param message to set
	 */
	public void addMessage(Message message){		
		if(messageHandler.shouldRollback(message)){
			this.rollbackOnly = true;
		}
		if(maxLevel < message.getMessageLevel()){
			maxLevel = message.getMessageLevel();
		}
		this.messages.add(message);		
	}	

	/**
	 * @return the messages
	 */
	public List<Message> getMessageList(){
		return this.messages;
	}
	
	/**
	 * @return if rollback only
	 */
	public boolean isRollbackOnly(){
		return rollbackOnly;
	}
	
	/**
	 * Initializes the context.
	 */
	public void initialize(){
		release();
		setCurrentInstance(this);	
	}
	
	/**
	 * Releases the context.
	 */
	public void release(){	
		rollbackOnly = false;
		messages = new ArrayList<Message>();
		messageHandler = null;
		setCurrentInstance(null);
	}

}
