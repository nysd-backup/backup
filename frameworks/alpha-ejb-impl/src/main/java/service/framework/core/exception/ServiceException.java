/**
 * Copyright 2011 the original author
 */
package service.framework.core.exception;

import core.exception.BusinessException;

/**
 * BusinessException for EJB.
 * 
 * <pre>
 * '@ServiceException is required to raise business exception.
 * Just RuntimeException and sub classes are system exeception in EJB.
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@javax.ejb.ApplicationException(rollback=true)
public class ServiceException extends BusinessException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param message the message
	 * @param cause the exception
	 */
	public ServiceException(String message , Throwable cause){
		super(message,cause);
	}
	
	/**
	 * @param message the message
	 */
	public ServiceException(String message){
		super(message);
	}
	
	/**
	 * Constructor
	 */
	public ServiceException(){
		super();
	}
	
}
