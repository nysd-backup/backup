/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;

/**
 * ResultSet用のフィルター.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ResultSetFilter<T> {

	/**
	 * @param data データ
	 */
	public T edit(T data);
}
