/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.free;

/**
 * BatchUpdateFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface BatchUpdateFactory {

	/**
	 * @return BatchUpdate
	 */
	public BatchUpdate createBatchUpdate();
}
