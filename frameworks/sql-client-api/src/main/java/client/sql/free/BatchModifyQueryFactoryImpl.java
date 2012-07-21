/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import client.sql.free.strategy.InternalQuery;

/**
 * BatchModifyQueryFactoryImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class BatchModifyQueryFactoryImpl implements BatchModifyQueryFactory{

	/** the internalQuery */
	private InternalQuery internalQuery;
	
	/**
	 * @see client.sql.free.BatchModifyQueryFactory#createBatchUpdate()
	 */
	@Override
	public BatchModifyQuery createBatchUpdate(){
		return new BatchModifyQueryImpl(internalQuery);
	}

	/**
	 * @param internalQuery the internalQuery to set
	 */
	public void setInternalQuery(InternalQuery internalQuery) {
		this.internalQuery = internalQuery;
	}
}
