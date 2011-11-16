/**
 * Copyright 2011 the original author
 */
package kosmos.framework.client.core.api.query.paging;

/**
 * The factor to create context.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface PagingContexFactory {

	/**
	 * @return the context
	 */
	public PagingContext create();
}
