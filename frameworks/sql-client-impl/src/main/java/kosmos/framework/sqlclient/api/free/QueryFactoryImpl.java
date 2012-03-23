/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import kosmos.framework.sqlclient.internal.free.InternalQuery;

/**
 * The factory to create the free writable query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryFactoryImpl implements QueryFactory{
	
	/** the internal query */
	private InternalQuery internalQuery;
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	public void setInternalQuery(InternalQuery internalQuery){
		this.internalQuery = internalQuery;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.QueryFactory#createQuery()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NativeQueryImpl createQuery() {
		NativeQueryImpl query = new NativeQueryImpl();
		query.setInternalQuery(internalQuery);
		return query;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.QueryFactory#createUpdate()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NativeUpdateImpl createUpdate() {
		NativeUpdateImpl update = new NativeUpdateImpl();
		update.setInternalQuery(internalQuery);
		return update;
	}
}
