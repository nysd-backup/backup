/**
 * Copyright 2011 the original author
 */
package client.sql.free;

/**
 * BatchUpsertFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface BatchUpsertFactory {

	/**
	 * @return BatchUpsert
	 */
	public BatchUpsert createBatchUpdate();
}
