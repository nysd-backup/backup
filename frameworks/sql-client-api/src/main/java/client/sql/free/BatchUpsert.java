/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import client.sql.free.AbstractNativeUpsert;
import client.sql.free.FreeUpsertParameter;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface BatchUpsert {

	public void addBatch(FreeUpsertParameter parameter);

	public void addBatch(AbstractNativeUpsert parameter);
	
	public int[] executeBatch();
}
