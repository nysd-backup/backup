/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.free;

import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.FreeUpdate;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface QueryFactoryWrapper {

	public <K extends FreeQuery,T extends AbstractFreeQuery<K>> T createQuery(Class<T> query);
	
	public <K extends FreeUpdate,T extends AbstractFreeUpdate<K>> T createUpdate(Class<T> update);
}
