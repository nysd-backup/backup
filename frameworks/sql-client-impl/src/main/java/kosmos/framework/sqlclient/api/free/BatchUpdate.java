/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import kosmos.framework.sqlclient.api.wrapper.free.AbstractNativeUpdate;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface BatchUpdate {

	public void addBatch(FreeUpdateParameter parameter);
	
	public void addBatch(NativeUpdate parameter);
	
	public void addBatch(AbstractNativeUpdate parameter);
	
	public int[] executeBatch();
}
