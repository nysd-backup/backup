/**
 * Copyright 2011 the original author
 */
package client.sql.free;

/**
 * BatchModifyQueryFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface BatchModifyQueryFactory {

	/**
	 * @return BatchModifyQuery
	 */
	public BatchModifyQuery createBatchUpdate();
}
