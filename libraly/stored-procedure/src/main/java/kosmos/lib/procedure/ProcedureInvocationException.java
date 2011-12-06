/**
 * Copyright 2011 the original author
 */
package kosmos.lib.procedure;

import java.sql.SQLException;

/**
 * The stored procedure exception.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ProcedureInvocationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ProcedureInvocationException(SQLException cause){
		super(cause);
	}
}
