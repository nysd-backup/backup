/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.exception;

/**
 * The exception for concurrency.
 * 
 * <pre>
 * 	OptimisticLockException or PessimisticLockException can be converted to this exception.
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
public class ConcurrentBusinessException extends AbstractBusinessException{

	/** the serial version uid */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message the message
	 * @param cause the cause
	 */
	public ConcurrentBusinessException(String message , Throwable cause){
		super(message,cause);
	}
	
	/**
	 * @param message the  message
	 */
	public ConcurrentBusinessException(String message){
		super(message);
	}
	
	/**
	 * Constructor
	 */
	public ConcurrentBusinessException(){
		super();
	}
}
