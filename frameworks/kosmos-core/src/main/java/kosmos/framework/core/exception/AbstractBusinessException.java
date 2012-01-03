/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.exception;

/**
 * The base of business exception.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class AbstractBusinessException extends RuntimeException{

	/** serialVersionUID */
	private static final long serialVersionUID = 4928387597757529973L;

	/**
	 * @param message the message
	 * @param cause the cause
	 */
	public AbstractBusinessException(String message , Throwable cause){
		super(message,cause);
	}
	
	/**
	 * @param message the  message
	 */
	public AbstractBusinessException(String message){
		super(message);
	}
	
	/**
	 * Constructor
	 */
	public AbstractBusinessException(){
		super();
	}

}
