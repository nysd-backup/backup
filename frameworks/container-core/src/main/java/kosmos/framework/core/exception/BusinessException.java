/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.exception;

import java.io.Serializable;

import kosmos.framework.core.dto.ReplyMessage;


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
public class BusinessException extends AbstractBusinessException{

	/** serialVersionUID */
	private static final long serialVersionUID = 4928387597757529973L;
	
	/** the data to reply to client */
	private Serializable replyData = null;

	/** the messageList */
	private ReplyMessage[] messageList = null;
	
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
	 * @param replyData the replyData to set
	 * @param message the message
	 */
	public BusinessException(String message,Serializable replyData){
		this(message);
	}
	
	/**
	 * @param replyData the replyData to set
	 */
	public BusinessException(Serializable replyData){
		this(null,replyData);
	}

	/**
	 * @param messageList the messageList to set
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
	
	/**
	 * @return　the reply data
	 */
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getReplyData(){
		return (T)replyData;
	}

}
