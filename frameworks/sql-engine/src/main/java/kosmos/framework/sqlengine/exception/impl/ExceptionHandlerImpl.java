/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.exception.impl;

import java.sql.SQLException;

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
	 * @see kosmos.framework.sqlengine.exception.ExceptionHandler#rethrow(java.sql.SQLException)
	 */
	@Override
	public RuntimeException rethrow(SQLException e) {
		return new SQLEngineException(e);
	}

}
