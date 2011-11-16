/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.query.DataSourceManager;

/**
 * An unit of work.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalUnitOfWork {
	
	/** the flag represent transaction is rolled back. Never to recover.*/
	private boolean rollbackOnly = false;
	
	/** the connection only for 'SQLEngine' */
	private Connection currentConnection = null;

	/**
	 * set the rollbackOnly true
	 */
	protected void setRollbackOnly() {
		this.rollbackOnly = true;
	}

	/**
	 * @return the rollbackOnly
	 */
	public boolean isRollbackOnly() {
		return rollbackOnly;
	}
	
	/**
	 * @return the connection available in current transaction
	 */
	public Connection getCurrentConnection(){
		if(currentConnection == null){
			try {
				currentConnection = ServiceLocator.lookupByInterface(DataSourceManager.class).getDataSource().getConnection();
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
		return currentConnection;
	}

	/**
	 * terminate the process.
	 */
	public void terminate(){
		if(currentConnection != null){
			try {
				currentConnection.close();
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	}
	

}
