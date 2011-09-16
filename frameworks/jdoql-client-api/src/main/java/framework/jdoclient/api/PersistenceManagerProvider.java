/**
 * Use is subject to license terms.
 */
package framework.jdoclient.api;

import javax.jdo.PersistenceManager;

/**
 * パーシステンスマネージャのソース.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface PersistenceManagerProvider {

	/**
	 * @return パーシステンスマネージャ
	 */
	public PersistenceManager getPersistenceManager();
}
