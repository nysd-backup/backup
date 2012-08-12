/**
 * Copyright 2011 the original author
 */
package core.exception;

/**
 * An exception that represent the poor implementation.
 * 
 * <pre>
 * There are bugs when this exception is thrown.
 * </pre>
 * 
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class PoorImplementationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param mes the message
	 */
	public PoorImplementationException(String mes){
		super(mes);
	}

	/**
	 * Constructor
	 */
	public PoorImplementationException(){
		super();
	}
	
	/**
	 * @param mes the message
	 * @param t the exception
	 */
	public PoorImplementationException(String mes , Throwable t){
		super(mes,t);
	}
	
}
