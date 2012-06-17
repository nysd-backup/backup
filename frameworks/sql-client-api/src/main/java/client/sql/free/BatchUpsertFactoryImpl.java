/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import client.sql.free.strategy.InternalQuery;

/**
 * BatchUpsertFactoryImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class BatchUpsertFactoryImpl implements BatchUpsertFactory{

	/** the internalQuery */
	private InternalQuery internalQuery;
	
	/**
	 * @see client.sql.free.BatchUpsertFactory#createBatchUpdate()
	 */
	@Override
	public BatchUpsert createBatchUpdate(){
		return new BatchUpsertImpl(internalQuery);
	}

	/**
	 * @param internalQuery the internalQuery to set
	 */
	public void setInternalQuery(InternalQuery internalQuery) {
		this.internalQuery = internalQuery;
	}
}
