/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api;

import java.sql.Connection;

/**
 * コネクション供給者.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ConnectionProvider {

	/**
	 * @return コネクション
	 */
	public Connection getConnection();
}
