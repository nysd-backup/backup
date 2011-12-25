/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import java.io.Serializable;

/**
 * The filter for <code>ResultSet</code>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ResultSetFilter extends Serializable{

	/**
	 * @param data the data of the one record
	 */
	public <T> T edit(T data);
}
