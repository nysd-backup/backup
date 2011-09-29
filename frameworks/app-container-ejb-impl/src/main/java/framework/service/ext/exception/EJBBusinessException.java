/**
 * Copyright 2011 the original author
 */
package framework.service.ext.exception;

import java.io.Serializable;

import javax.ejb.ApplicationException;

import framework.core.exception.application.BusinessException;

/**
 * Business exception for EJB.
 * 
 * <pre>
 * <code>BusinessException</code> is regarded as System Exception 
 * because the that is <code>RuntimeException</code> not annotated '@ApplicationException' to.
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ApplicationException(rollback=true)
public class EJBBusinessException extends BusinessException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param message the message
	 * @param cause the exception
	 */
	public EJBBusinessException(String message , Throwable cause){
		super(message,cause);
	}
	
	/**
	 * @param message the message
	 */
	public EJBBusinessException(String message){
		super(message);
	}
	
	/**
	 * @param message the message
	 * @param reply the reply 
	 */
	public EJBBusinessException(String message,Serializable reply){
		super(message,reply);
	}
	
	/**
	 * @param reply the reply
	 */
	public EJBBusinessException(Serializable reply){
		super(reply);
	}

}
