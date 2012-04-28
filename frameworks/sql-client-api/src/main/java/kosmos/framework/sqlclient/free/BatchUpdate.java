/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.free;

import kosmos.framework.sqlclient.free.AbstractNativeUpdate;
import kosmos.framework.sqlclient.free.FreeUpdateParameter;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface BatchUpdate {

	public void addBatch(FreeUpdateParameter parameter);

	public void addBatch(AbstractNativeUpdate parameter);
	
	public int[] executeBatch();
}
