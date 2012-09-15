/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.exception;

/**
 * The SQL engine exception.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param mes the message
	 */
	public QueryException(String mes){
		super(mes);
	}
	
	/**
	 * @param mes the message
	 * @param t the exception
	 */
	public QueryException(String mes,Throwable t){
		super(mes,t);
	}

	/**
	 * @param t the exception
	 */
	public QueryException(Throwable t){
		super(t);
	}
	
}
