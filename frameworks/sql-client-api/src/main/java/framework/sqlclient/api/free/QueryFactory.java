/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;


/**
 * Queryのファクトリ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface QueryFactory {

	/**
	 * Queryの作成<br/>
	 * SQLを記述する場合に使用する。
	 *
	 * @param <T>　型
	 * @param query クエリクラス
	 * @return クエリ
	 */
	public <K extends FreeQuery,T extends AbstractFreeQuery<K>> T createQuery(Class<T> query);
	

	/**
	 * Queryの作成<br/>
	 * SQLを記述する場合に使用する。
	 * 
	 * @param <T>　型
	 * @param query クエリクラス
	 * @return クエリ
	 */
	public <K extends FreeUpdate,T extends AbstractUpdate<K>> T createUpdate(Class<T> query);
	
}
