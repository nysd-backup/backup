/**
 * Copyright 2011 the original author
 */
package framework.core.exception;

/**
 * A exception that represent the poor implementation.
 * 
 * <pre>
 * your codes have any bugs when this exception is thrown.
 * see your code.
 * </pre>
 * 
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class PoorImplementationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_MESSAGE = "Poor Implementation : ";
	
	/**
	 * @param mes the message
	 */
	public PoorImplementationException(String mes){
		super(DEFAULT_MESSAGE+mes);
	}
	
	/**
	 * @param mes the message
	 * @param t the exception
	 */
	public PoorImplementationException(String mes , Throwable t){
		super(DEFAULT_MESSAGE+mes,t);
	}
	
}
