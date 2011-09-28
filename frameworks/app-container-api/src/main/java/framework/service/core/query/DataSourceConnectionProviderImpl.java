/**
 * Copyright 2011 the original author
 */
package framework.service.core.query;

import java.sql.Connection;
import framework.service.core.transaction.InternalUnitOfWork;
import framework.service.core.transaction.ServiceContext;
import framework.service.core.transaction.TransactionManagingContext;
import framework.sqlclient.api.ConnectionProvider;

/**
 * A connection provider only for 'SQLEngine'.
 * 
 * <pre>
 * Only available in JTA environment.
 * connection changes per 'getConnection' in not JTA environment.
 * Spring's 'DataSourceUtils' cannot resolve that problem.
 * 
 * In not JTA environment only one SQL is allowed in one transaction.
 * </pre>
 * 
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DataSourceConnectionProviderImpl implements ConnectionProvider{

	/**
	 * @see framework.sqlclient.api.ConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() {	
		TransactionManagingContext context = (TransactionManagingContext)ServiceContext.getCurrentInstance();
		InternalUnitOfWork current = context.getCurrentUnitOfWork();
		return current.getCurrentConnection();		
	}

}
