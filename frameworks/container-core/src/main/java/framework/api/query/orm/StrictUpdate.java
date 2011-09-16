package framework.api.query.orm;

import framework.core.entity.Metadata;

/**
 * StrictUpdate.
 *
 * @author yoshida-n
 * @version	2011/06/05 created.
 */
public interface StrictUpdate<T> extends AdvancedOrmUpdate<T>{

	/**
	 * 等価条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictUpdate<T> eq(Metadata<T, V> column, V value);

	/**
	 * 大なり条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictUpdate<T> gt(Metadata<T, V> column, V value);

	/**
	 * 小なり条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictUpdate<T> lt(Metadata<T, V> column, V value);

	/**
	 * 大なり=条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictUpdate<T> gtEq(Metadata<T, V> column, V value);

	/**
	 * 小なり=条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictUpdate<T> ltEq(Metadata<T, V> column, V value);

	/**
	 * BETWEE条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictUpdate<T> between(Metadata<T, V> column,V from, V to);
	
	/**
	 * SETの追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictUpdate<T> set(Metadata<T, V> column, V value);

	
	/**
	 * SETの追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract int update();


}