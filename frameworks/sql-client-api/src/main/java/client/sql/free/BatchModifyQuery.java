/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import client.sql.free.AbstractNativeModifyQuery;
import client.sql.free.FreeModifyQueryParameter;


/**
 * BatchModifyQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface BatchModifyQuery {

	/**
	 * @param parameter the parameter to add
	 */
	public void addBatch(FreeModifyQueryParameter parameter);

	/**
	 * @param query the query holding parameter.
	 */
	public void addBatch(AbstractNativeModifyQuery query);
	
	/**
	 * Executes batch update.
	 * @return result
	 */
	public int[] modify();
}
