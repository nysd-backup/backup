/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient;

import java.sql.Connection;

/**
 * Provides the <code>java.sql.Connection</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ConnectionProvider {

	/**
	 * @return the Connection
	 */
	public Connection getConnection();
}
