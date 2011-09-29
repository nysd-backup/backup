/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;

import java.io.Serializable;

/**
 * ResultSet用のフィルター.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ResultSetFilter<T> extends Serializable{

	/**
	 * @param data データ
	 */
	public T edit(T data);
}
