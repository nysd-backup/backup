/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.exception.impl;

import java.sql.SQLException;

import alpha.jdbc.exception.ExceptionHandler;
import alpha.jdbc.exception.QueryException;



/**
 * Handles the exception.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ExceptionHandlerImpl implements ExceptionHandler{

	/**
	 * @see alpha.jdbc.exception.ExceptionHandler#rethrow(java.sql.SQLException)
	 */
	@Override
	public RuntimeException rethrow(SQLException e) {
		return new QueryException(e);
	}

}
