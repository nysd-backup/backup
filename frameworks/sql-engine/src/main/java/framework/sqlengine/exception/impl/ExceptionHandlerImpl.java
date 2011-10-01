/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.exception.impl;

import framework.sqlengine.exception.ExceptionHandler;
import framework.sqlengine.exception.SQLEngineException;

/**
 * Handles the exception.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ExceptionHandlerImpl implements ExceptionHandler{

	/**
	 * @see framework.sqlengine.exception.ExceptionHandler#rethrow(java.lang.Exception)
	 */
	@Override
	public RuntimeException rethrow(Exception e) {
		if(e instanceof RuntimeException){
			return (RuntimeException)e;
		}else{
			return new SQLEngineException(e);
		}
	}

}
