package framework.sqlclient.api.orm;

import java.util.List;

import framework.sqlclient.api.Update;

/**
 * ORマッピング用クエリ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface OrmUpdate<T> extends Update{

	/**
	 * 等価条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract OrmUpdate<T> eq(String column, Object value);

	/**
	 * 大なり条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract OrmUpdate<T> gt(String column, Object value);

	/**
	 * 小なり条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract OrmUpdate<T> lt(String column, Object value);

	/**
	 * 大なり=条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract OrmUpdate<T> gtEq(String column, Object value);

	/**
	 * 小なり=条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract OrmUpdate<T> ltEq(String column, Object value);

	/**
	 * BETWEE条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param from from値
	 * @param to to値
	 * @return self
	 */
	public abstract OrmUpdate<T> between(String column, Object from, Object to);
	
	/**
	 * セット句
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public OrmUpdate<T> set(String column , Object value);
	
	/**
	 * セット句
	 * @param setString 値
	 * @return self 
	 */
	public OrmUpdate<T> set(String... setString);
	
	/**
	 * 条件
	 * @param filterString フィルター
	 * @return self
	 */
	public OrmUpdate<T> filter(String filterString);
	
	/**
	 * 更新
	 * @param set set句
	 * @param params パラメータ
	 * @return 件数
	 */
	public int execute(List<Object> set , Object... params);
	
	
	

}
