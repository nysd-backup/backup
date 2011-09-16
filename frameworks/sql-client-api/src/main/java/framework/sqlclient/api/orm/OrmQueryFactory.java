/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api.orm;


/**
 * ORMクエリのファクトリ.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface OrmQueryFactory {
	
	/**
	 * ORマッピング用クエリを作成する
	 * @param <T>　型
	 * @param entityClass エンティティクラス
	 * @return self
	 */
	public <T,Q extends OrmQuery<T>> Q createQuery(Class<T> entityClass);
	
	/**
	 * ORマッピング用クエリを作成する
	 * @param <T>　型
	 * @param entityClass エンティティクラス
	 * @return self
	 */
	public <T,Q extends OrmUpdate<T>> Q createUpdate(Class<T> entityClass);

}
