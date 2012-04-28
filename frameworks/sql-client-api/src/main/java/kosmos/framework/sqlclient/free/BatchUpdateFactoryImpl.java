/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.free;

import kosmos.framework.sqlclient.free.strategy.InternalQuery;

/**
 * BatchUpdateFactoryImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class BatchUpdateFactoryImpl implements BatchUpdateFactory{

	/** the internalQuery */
	private InternalQuery internalQuery;
	
	/**
	 * @see kosmos.framework.sqlclient.free.BatchUpdateFactory#createBatchUpdate()
	 */
	@Override
	public BatchUpdate createBatchUpdate(){
		return new BatchUpdateImpl(internalQuery);
	}

	/**
	 * @param internalQuery the internalQuery to set
	 */
	public void setInternalQuery(InternalQuery internalQuery) {
		this.internalQuery = internalQuery;
	}
}
