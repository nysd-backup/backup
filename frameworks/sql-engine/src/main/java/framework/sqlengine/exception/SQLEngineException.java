/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.exception;

/**
 * SQL例外.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SQLEngineException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param mes メッセージ
	 */
	public SQLEngineException(String mes){
		super(mes);
	}
	
	/**
	 * @param mes メッセージ
	 * @param t 例外
	 */
	public SQLEngineException(String mes,Throwable t){
		super(mes,t);
	}
	

	/**
	 * @param t 例外
	 */
	public SQLEngineException(Throwable t){
		super(t);
	}
	
}
