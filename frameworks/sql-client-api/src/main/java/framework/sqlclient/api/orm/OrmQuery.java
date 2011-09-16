package framework.sqlclient.api.orm;

import java.util.List;

import framework.sqlclient.api.Query;



/**
 * ORマッピング用クエリ.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface OrmQuery<T> extends Query{

	/**
	 * 等価条件の追加
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract OrmQuery<T> eq(String column, Object value);

	/**
	 * 大なり条件の追加
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract OrmQuery<T> gt(String column, Object value);

	/**
	 * 小なり条件の追加
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract OrmQuery<T> lt(String column, Object value);

	/**
	 * 大なり=条件の追加
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract OrmQuery<T> gtEq(String column, Object value);

	/**
	 * 小なり=条件の追加
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract OrmQuery<T> ltEq(String column, Object value);

	/**
	 * BETWEE条件の追加
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public abstract OrmQuery<T> between(String column, Object from, Object to);
	
	/**
	 * IN、CONTAIN条件の追加
	 * @param column カラム
	 * @param value 値
	 * @return self
	 */
	public OrmQuery<T> contains(String column, List<?> value);

	/**
	 * 昇順ソートの追加
	 * @param <V> 型
	 * @param column カラム
	 * @return self
	 */
	public abstract OrmQuery<T> asc(String column);

	/**
	 * 降順ソートの追加
	 * @param <V> 型
	 * @param column カラム
	 * @return self
	 */
	public abstract OrmQuery<T> desc(String column);

	/**
	 * @param filterString フィルター文字列
	 * @return self
	 */
	public abstract OrmQuery<T> filter(String filterString); 
	
	/**
	 * @param orderString order by 文字列
	 * @return self　
	 */
	public abstract OrmQuery<T> order(String orderString); 
	
	/**
	 * 単一検索
	 * @param パラメータ
	 * @return 1件取得
	 */
	public abstract T single(Object... params);
	
	/**
	 * 複数検索
	 * @param params パラメータ
	 * @return 検索結果
	 */
	public abstract List<T> list(Object... params);
	
	/**
	 * 単一検索.
	 * 複数件取得時エラー
	 * @param params パラメータ
	 * @return 検索結果
	 */
	public abstract T findAny(Object... params);
	
	/**
	 * 主キー検索
	 * @param pks　主キー
	 * @return 検索結果
	 */
	public abstract T find(Object... pks);

	/**
	 * 主キー検索
	 * @param pks　主キー
	 * @return 検索結果
	 */
	public abstract T findWithLockNoWait(Object... pks);

	/**
	 * 候補キー検索.
	 * 複数件取得時エラー
	 * @return　検索結果
	 */
	public abstract T findAny();

	/**
	 * 主キー指定存在チェック
	 * @param pks 主キー
	 * @return true:存在する
	 */
	public abstract boolean exists(Object... pks);

	/**
	 * 候補キー指定存在チェック
	 * @return true:存在する
	 */
	public abstract boolean existsByAny();

}