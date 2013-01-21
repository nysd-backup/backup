/**
 * Copyright 2011 the original author
 */
package service.core;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import alpha.query.criteria.EntityManagerImpl;



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
public class TxEntityManagerImpl extends EntityManagerImpl{
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}

	/**
	 * @see alpha.sqlclient.elink.orm.JTAEntityManagerImpl#getConnection()
	 */
	@Override
	public Connection getConnection() {	
		return DataSourceUtils.getConnection(dataSource);
	}

}
