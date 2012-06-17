/**
 * Copyright 2011 the original author
 */
package core.exception;


/**
 * The system exception.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class SystemException extends RuntimeException{

	/** serialVersionUID */
	private static final long serialVersionUID = 4928387597757529973L;
	
	/** the message code */
	private String messageId = null;
	
	/** the binding arguments */
	private Object[] args;
	
	public SystemException(){
		
	}
	
	/**
	 * @param messageId the message code
	 * @param args the arguments
	 */
	public SystemException(String messageId , Object... args){
		this.messageId = messageId;
		this.args = args;
	}
	
	/**
	 * @param message the messages
	 * @param cause the cause
	 * @param messageId the message code
	 * @param args the arguments
	 */
	public SystemException(String message , Throwable cause , String messageId , Object... args){
		super(message,cause);
		this.messageId = messageId;
		this.args = args;
	}
	/**
	 * @param message the message
	 * @param messageId the message code
	 * @param args the arguments
	 */
	public SystemException(String message , String messageId , Object... args){
		super(message);
		this.messageId = messageId;
		this.args = args;
	}
	
	/**
	 * @return the message code
	 */
	public String getMessageId(){
		return this.messageId;
	}
	
	/**
	 * @return the arguments
	 */
	public Object[] getArgs(){
		return this.args;
	}
}
