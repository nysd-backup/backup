/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.free;

import kosmos.framework.sqlclient.free.AbstractNativeUpsert;
import kosmos.framework.sqlclient.free.FreeUpdateParameter;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface BatchUpdate {

	public void addBatch(FreeUpdateParameter parameter);

	public void addBatch(AbstractNativeUpsert parameter);
	
	public int[] executeBatch();
}
