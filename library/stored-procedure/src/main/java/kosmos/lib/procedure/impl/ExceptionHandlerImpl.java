/**
 * Copyright 2011 the original author
 */
package kosmos.lib.procedure.impl;

import java.sql.SQLException;

import kosmos.lib.procedure.ExceptionHandler;
import kosmos.lib.procedure.ProcedureInvocationException;
import kosmos.lib.procedure.ProcedureTimeoutException;

/**
 * The exception handler.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ExceptionHandlerImpl implements ExceptionHandler{

	/**
	 * @see kosmos.lib.procedure.ExceptionHandler#handleException(java.sql.SQLException)
	 */
	@Override
	public void handleException(SQLException sqle) {
		if(sqle.getErrorCode() == 1013){
			throw new ProcedureTimeoutException();
		}
		throw new ProcedureInvocationException(sqle);
	}

}
