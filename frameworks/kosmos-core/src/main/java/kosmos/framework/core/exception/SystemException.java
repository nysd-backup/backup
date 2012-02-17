/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.exception;


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
	private int messageCode = -1;
	
	/** the binding arguments */
	private Object[] args;
	
	/**
	 * @param messageCode the message code
	 * @param args the arguments
	 */
	public SystemException(int messageCode , Object... args){
		this.messageCode = messageCode;
		this.args = args;
	}
	
	/**
	 * @param message the messages
	 * @param cause the cause
	 * @param messageCode the message code
	 * @param args the arguments
	 */
	public SystemException(String message , Throwable cause , int messageCode , Object... args){
		super(message,cause);
		this.messageCode = messageCode;
		this.args = args;
	}
	/**
	 * @param message the message
	 * @param messageCode the message code
	 * @param args the arguments
	 */
	public SystemException(String message , int messageCode , Object... args){
		super(message);
		this.messageCode = messageCode;
		this.args = args;
	}
	
	/**
	 * @return the message code
	 */
	public int getMessageCode(){
		return this.messageCode;
	}
	
	/**
	 * @return the arguments
	 */
	public Object[] getArgs(){
		return this.args;
	}
}
