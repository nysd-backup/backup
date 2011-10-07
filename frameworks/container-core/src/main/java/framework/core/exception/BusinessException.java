/**
 * Copyright 2011 the original author
 */
package framework.core.exception;

import java.io.Serializable;

import framework.api.dto.ReplyMessage;

/**
 * The business exception.
 * 
 * <pre>
 * In EJB environment this class is regarded as a system exception.
 * Set '@ApplicationException' to subclass of this to prevent the EJB container from throwing system exception. 
 *</pre>
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class BusinessException extends RuntimeException{

	/** serialVersionUID */
	private static final long serialVersionUID = 4928387597757529973L;

	/** the data to reply to WEB container */
	private Serializable reply;
	
	/** the messages */
	private ReplyMessage[] messageList;
	
	/**
	 * @param message the message
	 * @param cause the cause
	 */
	public BusinessException(String message , Throwable cause){
		super(message,cause);
	}
	
	/**
	 * @param message the  message
	 */
	public BusinessException(String message){
		super(message);
	}
	
	/**
	 * @param message the message
	 * @param reply the data to reply to WEB container
	 */
	public BusinessException(String message,Serializable reply){
		super(message);
		this.reply = reply;
	}
	
	/**
	 * @param reply the data to reply to WEB container
	 */
	public BusinessException(Serializable reply){
		this(null,reply);
	}

	/**
	 * @param <T> the type
	 * @return the data
	 */
	public Serializable getReply(){
		return reply;
	}

	/**
	 * @param message the messageList to set
	 */
	public void setMessageList(ReplyMessage[] messageList) {
		this.messageList = messageList;
	}

	/**
	 * @return the messageList
	 */
	public ReplyMessage[] getMessageList() {
		return messageList;
	}
}
