/**
 * Copyright 2011 the original author
 */
package sqlengine.exception;

/**
 * The SQL engine exception.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SQLEngineException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param mes the message
	 */
	public SQLEngineException(String mes){
		super(mes);
	}
	
	/**
	 * @param mes the message
	 * @param t the exception
	 */
	public SQLEngineException(String mes,Throwable t){
		super(mes,t);
	}

	/**
	 * @param t the exception
	 */
	public SQLEngineException(Throwable t){
		super(t);
	}
	
}
