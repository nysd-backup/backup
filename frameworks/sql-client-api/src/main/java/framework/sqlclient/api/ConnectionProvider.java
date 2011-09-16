/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api;

import java.sql.Connection;

/**
 * コネクション供給者.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ConnectionProvider {

	/**
	 * @return コネクション
	 */
	public Connection getConnection();
}
