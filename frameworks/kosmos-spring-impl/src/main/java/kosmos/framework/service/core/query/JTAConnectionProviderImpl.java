/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import kosmos.framework.sqlclient.ConnectionProvider;

/**
 * A connection provider only for 'SQLEngine'.
 * 
 * <pre>
 * Only use this when the JTADataSourceTransactionManager is active.
 * </pre>
 * 
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class JTAConnectionProviderImpl implements ConnectionProvider{
	
	private DataSource dataSource;
	
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}

	/**
	 * @see kosmos.framework.sqlclient.ConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() {	
		try{
			return dataSource.getConnection();
		}catch(SQLException sqle){
			throw new IllegalStateException("failed to get connection ",sqle);
		}
	}

}
