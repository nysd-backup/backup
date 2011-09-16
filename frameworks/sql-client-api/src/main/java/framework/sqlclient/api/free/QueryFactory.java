/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api.free;


/**
 * Queryのファクトリ.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface QueryFactory {

	/**
	 * Queryの作成<br/>
	 * SQLを記述する場合に使用する。
	 *
	 * @param <T>　型
	 * @param query クエリ
	 * @return クエリ
	 */
	public <K extends FreeQuery,T extends AbstractFreeQuery<K>> T createQuery(Class<T> query);
	

	/**
	 * Queryの作成<br/>
	 * SQLを記述する場合に使用する。
	 * 
	 * @param <T>　型
	 * @param entityClass エンティティクラス
	 * @return クエリ
	 */
	public <K extends FreeUpdate,T extends AbstractUpdate<K>> T createUpdate(Class<T> query);
	
}
