/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import client.sql.free.AbstractNativeModifyQuery;
import client.sql.free.FreeModifyQueryParameter;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface BatchModifyQuery {

	public void addBatch(FreeModifyQueryParameter parameter);

	public void addBatch(AbstractNativeModifyQuery parameter);
	
	public int[] modify();
}
