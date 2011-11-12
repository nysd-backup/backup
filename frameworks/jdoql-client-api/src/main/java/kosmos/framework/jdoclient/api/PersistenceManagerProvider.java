/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jdoclient.api;

import javax.jdo.PersistenceManager;

/**
 * Provides the <code>PersistenceManager</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface PersistenceManagerProvider {

	/**
	 * @return the PersistenceManager
	 */
	public PersistenceManager getPersistenceManager();
}
