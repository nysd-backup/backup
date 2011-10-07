/**
 * Copyright 2011 the original author
 */
package framework.service.ext.exception;

import java.io.Serializable;

import framework.core.exception.BusinessException;

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
public class ApplicationException extends BusinessException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param message the message
	 * @param cause the exception
	 */
	public ApplicationException(String message , Throwable cause){
		super(message,cause);
	}
	
	/**
	 * @param message the message
	 */
	public ApplicationException(String message){
		super(message);
	}
	
	/**
	 * @param message the message
	 * @param reply the reply 
	 */
	public ApplicationException(String message,Serializable reply){
		super(message,reply);
	}
	
	/**
	 * @param reply the reply
	 */
	public ApplicationException(Serializable reply){
		super(reply);
	}

}
