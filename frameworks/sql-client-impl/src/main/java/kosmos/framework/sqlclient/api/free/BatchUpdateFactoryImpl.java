/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import kosmos.framework.sqlclient.api.ConnectionProvider;
import kosmos.framework.sqlengine.facade.SQLEngineFacade;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class BatchUpdateFactoryImpl implements BatchUpdateFactory{

	/** the SQLEngineFacade */
	private SQLEngineFacade facade;
	
	/** the ConnectionProvider */
	private ConnectionProvider provider;
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.BatchUpdateFactory#createBatchUpdate()
	 */
	@Override
	public BatchUpdate createBatchUpdate(){
		return new BatchUpdateImpl(facade,provider);
	}

	/**
	 * @param facade the facade to set
	 */
	public void setSqlEngineFacade(SQLEngineFacade facade) {
		this.facade = facade;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setConnectionProvider(ConnectionProvider provider) {
		this.provider = provider;
	}
}
