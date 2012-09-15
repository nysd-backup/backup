/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.strategy;

/**
 * The filter for <code>ResultSet</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface RecordFilter {

	/**
	 * @param data the data of one record
	 */
	<T> T edit(T data);
}
