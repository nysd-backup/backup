package framework.api.query.orm;

import java.util.List;

import framework.core.entity.Metadata;

/**
 * 拡張ORMクエリ.
 *
 * @author yoshida-n
 * @version	2011/06/05 created.
 */
public interface StrictQuery<T> extends AdvancedOrmQuery<T>{

	/**
	 * 等価条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictQuery<T> eq(Metadata<T, V> column, V value);

	/**
	 * 大なり条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictQuery<T> gt(Metadata<T, V> column, V value);

	/**
	 * 小なり条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictQuery<T> lt(Metadata<T, V> column, V value);

	/**
	 * 大なり=条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictQuery<T> gtEq(Metadata<T, V> column, V value);

	/**
	 * 小なり=条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictQuery<T> ltEq(Metadata<T, V> column, V value);

	/**
	 * BETWEE条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictQuery<T> between(Metadata<T, V> column,
			V from, V to);

	/**
	 * IN、CONTAINS条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictQuery<T> containsList(Metadata<T, V> column, List<V> value);
	
	/**
	 * IN、CONTAINS条件の追加
	 * @param <V> 型
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract <V> StrictQuery<T> contains(Metadata<T, V> column, V... value);
	
	/**
	 * 昇順ソートの追加
	 * @param <V> 型
	 * @param column カラム
	 * @return self
	 */
	public abstract StrictQuery<T> asc(Metadata<T, ?> column);

	/**
	 * 降順ソートの追加
	 * @param <V> 型
	 * @param column カラム
	 * @return self
	 */
	public abstract StrictQuery<T> desc(Metadata<T, ?> column);

	/**
	 * AND条件の追加
	 * @return self
	 */
	public abstract StrictQuery<T> and();
	
	/**
	 * OR条件の追加
	 * @return self
	 */
	public abstract StrictQuery<T> or();
	
	/**
	 * 候補キー検索.
	 * 複数件取得時エラー
	 * @return　検索結果
	 */
	public abstract T findAny();

	/**
	 * 任意条件検索.
	 * @return 検索結果
	 */
	public abstract List<T> getResultList();

	/**
	 * 先頭行検索
	 * @return 先頭行
	 */
	public abstract T getSingleResult();

	/**
	 * 候補キー指定存在チェック
	 * @return true:存在する
	 */
	public abstract boolean existsByAny();	

	/**
	 * @return true:存在する
	 */
	public abstract boolean exists();
}