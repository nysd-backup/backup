/**
 * Copyright 2011 the original author
 */
package framework.jdoclient.internal;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import framework.jdoclient.api.PersistenceManagerProvider;

/**
 * パーシステンスマネージャのソース.
 * PersistenceManagerFactoryはSingletonでよいのでこのクラスもSingletonでよい。
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class PersistenceManagerProviderImpl implements PersistenceManagerProvider{

	/** パーシステンスマネージャ */
	private PersistenceManagerFactory persistenceManagerFactory;

	/**
	 * @param persistenceManagerFactory the persistenceManagerFactory to set
	 */
	public void setPersistenceManagerFactory(PersistenceManagerFactory persistenceManagerFactory) {
		this.persistenceManagerFactory = persistenceManagerFactory;
	}	
	
	/**
	 * @see framework.jdoclient.api.PersistenceManagerProvider#getPersistenceManager()
	 */
	public PersistenceManager getPersistenceManager(){
		return persistenceManagerFactory.getPersistenceManager();
	}


}
