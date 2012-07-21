/**
 * Copyright 2011 the original author
 */
package core.exception;

import core.message.MessageResult;


/**
 * The system exception.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class SystemException extends RuntimeException{

	/** serialVersionUID */
	private static final long serialVersionUID = 4928387597757529973L;
	
	private MessageResult messaegResult;
	
	public SystemException(){
		
	}
	
	/**
	 * @param messageId the message code
	 * @param args the arguments
	 */
	public SystemException(MessageResult messaegResult){
		this.messaegResult = messaegResult;
	}
	
	/**
	 * @param message the messages
	 * @param cause the cause
	 * @param messageId the message code
	 * @param args the arguments
	 */
	public SystemException(String message , Throwable cause ,MessageResult messaegResult){
		super(message,cause);
		this.messaegResult = messaegResult;
	}
	/**
	 * @param message the message
	 * @param messageId the message code
	 * @param args the arguments
	 */
	public SystemException(String message , MessageResult messaegResult){
		super(message);
		this.messaegResult = messaegResult;
	}
	
	/**
	 * @return the message code
	 */
	public MessageResult getMessageResult(){
		return this.messaegResult;
	}
	
}
