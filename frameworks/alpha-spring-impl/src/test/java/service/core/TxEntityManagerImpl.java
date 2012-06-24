/**
 * Copyright 2011 the original author
 */
package service.core;

import java.sql.Connection;

import org.springframework.jdbc.datasource.DataSourceUtils;

import client.sql.orm.JTAEntityManagerImpl;


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
public class TxEntityManagerImpl extends JTAEntityManagerImpl{

	/**
	 * @see client.sql.orm.JTAEntityManagerImpl#getConnection()
	 */
	@Override
	public Connection getConnection() {	
		return DataSourceUtils.getConnection(getDataSoure());
	}

}
