/**
 * Copyright 2011 the original author
 */
package alpha.framework.core.exception;

import alpha.framework.core.message.Message;


/**
 * The system exception.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class SystemException extends RuntimeException{

	/** serialVersionUID */
	private static final long serialVersionUID = 4928387597757529973L;
	
	private Message messaegResult;
	
	public SystemException(){
		
	}
	
	/**
	 * @param messageId the message code
	 * @param args the arguments
	 */
	public SystemException(Message messaegResult){
		this.messaegResult = messaegResult;
	}
	
	/**
	 * @param message the messages
	 * @param cause the cause
	 * @param messageId the message code
	 * @param args the arguments
	 */
	public SystemException(String message , Throwable cause ,Message messaegResult){
		super(message,cause);
		this.messaegResult = messaegResult;
	}
	/**
	 * @param message the message
	 * @param messageId the message code
	 * @param args the arguments
	 */
	public SystemException(String message , Message messaegResult){
		super(message);
		this.messaegResult = messaegResult;
	}
	
	/**
	 * @return the message code
	 */
	public Message getMessageResult(){
		return this.messaegResult;
	}
	
}
