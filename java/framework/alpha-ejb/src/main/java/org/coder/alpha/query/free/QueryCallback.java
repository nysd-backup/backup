/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.free;

/**
 * Call backs the process after the query.
 *
 * @author yoshida-n
 * @version	1.0
 */
public abstract class QueryCallback<T> implements AutoCloseable {
	
	/**
	 * Event before reading.
	 */
	public void preRead(){
		
	}

	/**
	 * Handles the one record.
	 * @param oneRecord the record
	 * @param rowIndex incremented count
	 */
	public abstract void handleRow(T oneRecord , long rowIndex);
	
	
	/**
	 * Event after reading.
	 */
	public void postRead(long count) {
		
	}
	
}
