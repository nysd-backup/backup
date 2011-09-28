/**
 * Copyright 2011 the original author
 */
package framework.api.query.orm;

import framework.core.entity.Metadata;

/**
 * ORM更新.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface StrictUpdate<T> extends AdvancedOrmUpdate<T>{

	/**
	 * 等価条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public <V> StrictUpdate<T> eq(Metadata<T, V> column, V value);

	/**
	 * 大なり条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public <V> StrictUpdate<T> gt(Metadata<T, V> column, V value);

	/**
	 * 小なり条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public <V> StrictUpdate<T> lt(Metadata<T, V> column, V value);

	/**
	 * 大なり=条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public <V> StrictUpdate<T> gtEq(Metadata<T, V> column, V value);

	/**
	 * 小なり=条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public <V> StrictUpdate<T> ltEq(Metadata<T, V> column, V value);

	/**
	 * BETWEE条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param from from値
	 * @param to to値
	 * @return self
	 */
	public <V> StrictUpdate<T> between(Metadata<T, V> column,V from, V to);
	
	/**
	 * SETの追加.
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public <V> StrictUpdate<T> set(Metadata<T, V> column, V value);

	
	/**
	 * 更新.
	 * 
	 * @return 更新件数
	 */
	public int update();


}
