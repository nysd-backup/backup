/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.exception.impl;

import kosmos.framework.sqlengine.exception.ExceptionHandler;
import kosmos.framework.sqlengine.exception.SQLEngineException;

/**
 * Handles the exception.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ExceptionHandlerImpl implements ExceptionHandler{

	/**
	 * @see kosmos.framework.sqlengine.exception.ExceptionHandler#rethrow(java.lang.Throwable)
	 */
	@Override
	public RuntimeException rethrow(Throwable e) {
		if(e instanceof RuntimeException){
			return (RuntimeException)e;
		}else if(e instanceof Error){
			throw (Error)e;
		}else{
			return new SQLEngineException(e);
		}
	}

}
