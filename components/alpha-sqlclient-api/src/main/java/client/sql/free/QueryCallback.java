/**
 * Copyright 2011 the original author
 */
package client.sql.free;

/**
 * Call backs the process after the query.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface QueryCallback<T> {

	/**
	 * Handles the one record.
	 * @param oneRecord the record
	 * @param rowIndex incremented count
	 */
	void handleRow(T oneRecord , long rowIndex);
	
}
