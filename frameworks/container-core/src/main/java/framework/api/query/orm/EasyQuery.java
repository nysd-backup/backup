/**
 * Use is subject to license terms.
 */
package framework.api.query.orm;

import java.util.List;


/**
 * EasyQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface EasyQuery<T> extends AdvancedOrmQuery<T>{

	/**
	 * WHERE句の追加
	 * @return self
	 */
	public EasyQuery<T> filter(String filterString);
	
	/**
	 * ORDER BY句の追加
	 * @return self
	 */
	public EasyQuery<T> order(String orderString);
	
	/**
	 * 一覧検索
	 * @param params
	 * @return 結果
	 */
	public List<T> list(Object... params);
	
	/**
	 * 単一検索.
	 * 複数件取得時はエラー
	 * 
	 * @param params パラメータ
	 * @return 結果
	 */
	public T findAny(Object... params);
	
	/**
	 * 単一検索
	 * @param params パラメタ
	 * @return 結果
	 */
	public T single(Object... params);
	
	/**
	 * 存在チェック.
	 * 複数件取得時はエラー
	 * 
	 * @param params パラメタ
	 * @return 結果
	 */
	public boolean existsByAny(Object... params);

	/**
	 * 存在チェック.
	 * @param params パラメタ
	 * @return 結果
	 */
	public boolean existsByList(Object... params);

}
