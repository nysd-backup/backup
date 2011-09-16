/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.api;

import javax.persistence.EntityManager;

/**
 * エンティティマネージャのソース.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface EntityManagerProvider {

	/**
	 * @return エンティティマネージャ
	 */
	public EntityManager getEntityManager();
}
