/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.exception;

/**
 * 例外ハンドラ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ExceptionHandler {

	/**
	 * @param t 例外
	 * @return rethrow
	 */
	public RuntimeException rethrow(Exception t);
}
