/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.free;

/**
 * The filter for <code>ResultSet</code>.
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface RecordFilter {

	/**
	 * @param data the data of one record
	 */
	<T> T edit(T data);
}
