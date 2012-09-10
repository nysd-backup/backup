/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import java.util.ArrayList;
import java.util.List;

import client.sql.free.strategy.InternalQuery;



/**
 * BatchModifyQueryImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class BatchModifyQueryImpl implements BatchModifyQuery{
	
	/** parameters */
	private List<FreeModifyQueryParameter> parameters = new ArrayList<FreeModifyQueryParameter>();
	
	/** the internal query */
	private InternalQuery internalQuery;
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	BatchModifyQueryImpl(InternalQuery internalQuery){
		this.internalQuery = internalQuery;
	}

	/**
	 * @see client.sql.free.BatchModifyQuery#addBatch(client.sql.free.NativeUpdate)
	 */
	@Override
	public void addBatch(FreeModifyQueryParameter parameter) {
		parameters.add(parameter);
	}

	/**
	 * @see client.sql.free.BatchModifyQuery#addBatch(client.sql.free.AbstractNativeModifyQuery)
	 */
	@Override
	public void addBatch(AbstractNativeModifyQuery parameter) {
		addBatch(parameter.getParameter());
	}


	/**
	 * @see client.sql.free.BatchModifyQuery#executeBatch()
	 */
	@Override
	public int[] modify() {
		try{
			return internalQuery.executeBatch(parameters);
		}finally{
			parameters.clear();
		}
	}

}
