/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import java.util.ArrayList;
import java.util.List;

import client.sql.free.strategy.InternalQuery;



/**
 * BatchUpsertImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class BatchUpsertImpl implements BatchUpsert{
	
	/** parameters */
	private List<FreeUpsertParameter> parameters = new ArrayList<FreeUpsertParameter>();
	
	/** the internal query */
	private InternalQuery internalQuery;
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	BatchUpsertImpl(InternalQuery internalQuery){
		this.internalQuery = internalQuery;
	}

	/**
	 * @see client.sql.free.BatchUpsert#addBatch(client.sql.free.NativeUpdate)
	 */
	@Override
	public void addBatch(FreeUpsertParameter parameter) {
		parameters.add(parameter);
	}

	/**
	 * @see client.sql.free.BatchUpsert#addBatch(client.sql.free.AbstractNativeUpsert)
	 */
	@Override
	public void addBatch(AbstractNativeUpsert parameter) {
		addBatch(parameter.getParameter());
	}


	/**
	 * @see client.sql.free.BatchUpsert#executeBatch()
	 */
	@Override
	public int[] executeBatch() {
		try{
			return internalQuery.executeBatch(parameters);
		}finally{
			parameters.clear();
		}
	}

}
