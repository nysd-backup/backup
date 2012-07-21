/**
 * Copyright 2011 the original author
 */
package service.framework.core.exception;

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
public class BusinessException extends core.exception.BusinessException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param message the message
	 * @param cause the exception
	 */
	public BusinessException(String message , Throwable cause){
		super(message,cause);
	}
	
	/**
	 * @param message the message
	 */
	public BusinessException(String message){
		super(message);
	}
	
	/**
	 * Constructor
	 */
	public BusinessException(){
		super();
	}
	
}
