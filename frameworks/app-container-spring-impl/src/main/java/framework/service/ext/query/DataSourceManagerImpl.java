/**
 * Copyright 2011 the original author
 */
package framework.service.ext.query;

import javax.sql.DataSource;

import framework.service.core.query.DataSourceManager;

/**
 * Get a <code>DataSource</code>.
 * 
 * <pre>
 * Gets only from JTA connection pool.
 * Use <code>oracle.jdbc.xa.client.OracleXADataSource</code> in oracle.
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DataSourceManagerImpl implements DataSourceManager{

	/** the dataSource */
	private DataSource dataSource;
	
	/**
	 * @param dataSource　the dataSource to set
	 */
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	/**
	 * @see framework.service.core.query.DataSourceManager#getDataSource()
	 */
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

}
