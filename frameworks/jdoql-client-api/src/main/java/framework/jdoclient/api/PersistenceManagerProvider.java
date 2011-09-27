/**
 * Copyright 2011 the original author
 */
package framework.jdoclient.api;

import javax.jdo.PersistenceManager;

/**
 * パーシステンスマネージャのソース.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface PersistenceManagerProvider {

	/**
	 * @return パーシステンスマネージャ
	 */
	public PersistenceManager getPersistenceManager();
}
