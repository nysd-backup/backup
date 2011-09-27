/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer;

/**
 * 検索結果の行ごとに実行されるフィルター.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface RecordFilter<T> {


	/**
	 * @param data データ
	 */
	public T edit(T data);
}
