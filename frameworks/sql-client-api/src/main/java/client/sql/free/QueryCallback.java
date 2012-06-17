/**
 * Copyright 2011 the original author
 */
package client.sql.free;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface QueryCallback<T> {

	void handleRow(T oneRecord , long rowIndex);
	
}
