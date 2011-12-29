/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import java.sql.Connection;

import javax.sql.DataSource;

import kosmos.framework.sqlclient.api.ConnectionProvider;

import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * A connection provider only for 'SQLEngine'.
 * 
 * <pre>
 * Needless to use this in JTA environment.
 * Only use this when the DataSourceTransactionManager is active.
 * </pre>
 * 
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class TxDataSourceConnectionProviderImpl implements ConnectionProvider{
	
	private DataSource dataSource;
	
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.ConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() {	
		return  DataSourceUtils.getConnection(dataSource);
	}

}
