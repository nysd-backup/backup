/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.exception;


/**
 * The unexpected exception thrown if no result is found.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class UnexpectedNoDataFoundException extends SystemException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param message the message
	 * @param cause the cause
	 */
	public UnexpectedNoDataFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param message the message
	 */
	public UnexpectedNoDataFoundException(String message) {
		super(message);
	}
}
