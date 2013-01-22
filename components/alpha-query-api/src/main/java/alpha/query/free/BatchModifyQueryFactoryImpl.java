/**
 * Copyright 2011 the original author
 */
package alpha.query.free;

import alpha.query.free.gateway.PersistenceGateway;

/**
 * BatchModifyQueryFactoryImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class BatchModifyQueryFactoryImpl implements BatchModifyQueryFactory{

	/** the internalQuery */
	private PersistenceGateway gateway;
	
	/**
	 * @see alpha.query.free.BatchModifyQueryFactory#createBatchUpdate()
	 */
	@Override
	public BatchModifyQuery createBatchUpdate(){
		return new BatchModifyQueryImpl(gateway);
	}

	/**
	 * @param gateway the gateway to set
	 */
	public void setPersistenceGateway(PersistenceGateway gateway) {
		this.gateway = gateway;
	}
}
