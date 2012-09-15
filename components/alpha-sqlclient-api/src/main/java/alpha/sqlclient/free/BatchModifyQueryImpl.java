/**
 * Copyright 2011 the original author
 */
package alpha.sqlclient.free;

import java.util.ArrayList;
import java.util.List;

import alpha.sqlclient.free.strategy.InternalQuery;




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
	 * @see alpha.sqlclient.free.BatchModifyQuery#addBatch(alpha.sqlclient.free.NativeUpdate)
	 */
	@Override
	public void addBatch(FreeModifyQueryParameter parameter) {
		parameters.add(parameter);
	}

	/**
	 * @see alpha.sqlclient.free.BatchModifyQuery#addBatch(alpha.sqlclient.free.AbstractNativeModifyQuery)
	 */
	@Override
	public void addBatch(AbstractNativeModifyQuery parameter) {
		addBatch(parameter.getParameter());
	}


	/**
	 * @see alpha.sqlclient.free.BatchModifyQuery#executeBatch()
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
