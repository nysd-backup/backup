/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.exception;

import kosmos.framework.core.exception.ConcurrentBusinessException;

/**
 * BusinessException for EJB.
 * 
 * <pre>
 * '@ApplicationException is required to raise business exception.
 * Just RuntimeException and sub classes are system exeception in EJB.
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@javax.ejb.ApplicationException(rollback=true)
public class ConcurrentApplicationException extends ConcurrentBusinessException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param message the message
	 * @param cause the exception
	 */
	public ConcurrentApplicationException(String message , Throwable cause){
		super(message,cause);
	}
	
	/**
	 * @param message the message
	 */
	public ConcurrentApplicationException(String message){
		super(message);
	}
	
	/**
	 * Constructor
	 */
	public ConcurrentApplicationException(){
		super();
	}
	
}
