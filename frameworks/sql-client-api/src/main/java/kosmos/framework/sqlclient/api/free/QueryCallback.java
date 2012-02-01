/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface QueryCallback<T> {

	void handleRow(T oneRecord , long rowIndex);
	
}
