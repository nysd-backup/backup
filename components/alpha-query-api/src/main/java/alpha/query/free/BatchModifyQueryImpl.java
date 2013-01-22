/**
 * Copyright 2011 the original author
 */
package alpha.query.free;

import java.util.ArrayList;
import java.util.List;

import alpha.query.free.gateway.PersistenceGateway;




/**
 * BatchModifyQueryImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class BatchModifyQueryImpl implements BatchModifyQuery{
	
	/** parameters */
	private List<ModifyingConditions> parameters = new ArrayList<ModifyingConditions>();
	
	/** the internal query */
	private PersistenceGateway gateway;
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	BatchModifyQueryImpl(PersistenceGateway gateway){
		this.gateway = gateway;
	}

	/**
	 * @see alpha.query.free.BatchModifyQuery#addBatch(alpha.query.free.NativeUpdate)
	 */
	@Override
	public void addBatch(ModifyingConditions parameter) {
		parameters.add(parameter);
	}

	/**
	 * @see alpha.query.free.BatchModifyQuery#addBatch(alpha.query.free.AbstractNativeModifyQuery)
	 */
	@Override
	public void addBatch(AbstractNativeModifyQuery parameter) {
		addBatch(parameter.getParameter());
	}


	/**
	 * @see alpha.query.free.BatchModifyQuery#executeBatch()
	 */
	@Override
	public int[] modify() {
		try{
			return gateway.executeBatch(parameters);
		}finally{
			parameters.clear();
		}
	}

}
