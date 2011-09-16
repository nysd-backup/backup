/**
 * Use is subject to license terms.
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
 * @version	created.
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
