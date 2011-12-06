/**
 * Copyright 2011 the original author
 */
package kosmos.lib.procedure;

import java.sql.SQLException;

/**
 * ExceptionHandler.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ExceptionHandler {

	/**
	 * @param sqle the SQLException
	 */
	public void handleException(SQLException sqle);
}
