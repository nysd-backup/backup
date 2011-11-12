/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jdoclient.internal;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import kosmos.framework.jdoclient.api.PersistenceManagerProvider;


/**
 * Provides the <code>PersistenceManager</code>.
 * 
 * <pre>
 * This class can be singleton.
 * </pre>
 * 
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class PersistenceManagerProviderImpl implements PersistenceManagerProvider{

	/** the PersistenceManagerFactory */
	private PersistenceManagerFactory persistenceManagerFactory;

	/**
	 * @param persistenceManagerFactory the persistenceManagerFactory to set
	 */
	public void setPersistenceManagerFactory(PersistenceManagerFactory persistenceManagerFactory) {
		this.persistenceManagerFactory = persistenceManagerFactory;
	}	
	
	/**
	 * @see kosmos.framework.jdoclient.api.PersistenceManagerProvider#getPersistenceManager()
	 */
	public PersistenceManager getPersistenceManager(){
		return persistenceManagerFactory.getPersistenceManager();
	}


}
