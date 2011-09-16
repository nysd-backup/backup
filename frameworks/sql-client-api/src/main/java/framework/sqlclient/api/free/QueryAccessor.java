/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api.free;



/**
 * デベロッパーに公開しないAPIへのアクセサ.
 *
 * @author yoshida-n
 * @version	created.
 */
public class QueryAccessor {
	
	/**
	 * デリゲーティングクエリの設定.
	 * 
	 * @param <D> 型
	 * @param query クエリ
	 * @param delegate クエリ
	 */
	public static <D extends FreeQuery> void setDelegate(AbstractFreeQuery<D> query , D delegate){
		query.setDelegate(delegate);
	}
	

	/**
	 * デリゲーティングクエリの取得.
	 * 
	 * @param <D> 型
	 * @param query クエリ
	 * @return delegate
	 */
	public static <D extends FreeQuery> D getDelegate(AbstractFreeQuery<D> query){
		return query.getDelegate();
	}
	
	/**
	 * デリゲーティングクエリの設定.
	 * @param <D> 型
	 * @param query クエリ
	 * @param delegate クエリ
	 */
	public static <D extends FreeUpdate> void setDelegate(AbstractUpdate<D> query , D delegate){
		query.setDelegate(delegate);
	}
		
}
