/**
 * Copyright 2011 the original author
 */
package core.exception;

import java.util.List;

import core.message.MessageResult;



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

	/** the messageList */
	private List<MessageResult> messageList = null;
	
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
	 * Constructor
	 */
	public BusinessException(){
		super();
	}
	
	/**
	 * @param messageList the messageList to set
	 */
	public void setMessageList(List<MessageResult> messageList) {
		this.messageList = messageList;
	}

	/**
	 * @return the messageList
	 */
	public List<MessageResult> getMessageList() {
		return messageList;
	}

}
