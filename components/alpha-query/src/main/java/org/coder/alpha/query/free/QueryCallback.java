/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free;

/**
 * Call backs the process after the query.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface QueryCallback<T> {
	
	void initialize();

	/**
	 * Handles the one record.
	 * @param oneRecord the record
	 * @param rowIndex incremented count
	 */
	void handleRow(T oneRecord , long rowIndex);
	
	/**
	 * Terminates the callback.
	 */
	void terminate();
	
}
