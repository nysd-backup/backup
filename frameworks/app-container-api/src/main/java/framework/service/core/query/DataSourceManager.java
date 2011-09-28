/**
 * Copyright 2011 the original author
 */
package framework.service.core.query;

import javax.sql.DataSource;

/**
 * Get a <code>DataSource</code>.
 * 
 * <pre>
 * Get only from JTA connection pool.
 * Use <code>oracle.jdbc.xa.client.OracleXADataSource</code> in oracle.
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface DataSourceManager {

	/**
	 * @return the <code>DataSource</code>
	 */
	public DataSource getDataSource();
}
