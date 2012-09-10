/**
 * Copyright 2011 the original author
 */
package sqlengine.exception;

import java.sql.SQLException;

/**
 * Handles the <code>SQLException</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ExceptionHandler {

	/**
	 * @param t the exception
	 * @return the exception
	 */
	RuntimeException rethrow(SQLException t);
}
