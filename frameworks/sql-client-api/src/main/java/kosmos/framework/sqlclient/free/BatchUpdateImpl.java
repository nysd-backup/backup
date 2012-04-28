/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.free;

import java.util.ArrayList;
import java.util.List;

import kosmos.framework.sqlclient.free.strategy.InternalQuery;

/**
 * BatchUpdateImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class BatchUpdateImpl implements BatchUpdate{
	
	/** parameters */
	private List<FreeUpdateParameter> parameters = new ArrayList<FreeUpdateParameter>();
	
	/** the internal query */
	private InternalQuery internalQuery;
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	BatchUpdateImpl(InternalQuery internalQuery){
		this.internalQuery = internalQuery;
	}

	/**
	 * @see kosmos.framework.sqlclient.free.BatchUpdate#addBatch(kosmos.framework.sqlclient.free.NativeUpdate)
	 */
	@Override
	public void addBatch(FreeUpdateParameter parameter) {
		parameters.add(parameter);
	}

	/**
	 * @see kosmos.framework.sqlclient.free.BatchUpdate#addBatch(kosmos.framework.sqlclient.free.AbstractNativeUpdate)
	 */
	@Override
	public void addBatch(AbstractNativeUpdate parameter) {
		addBatch(parameter.getParameter());
	}


	/**
	 * @see kosmos.framework.sqlclient.free.BatchUpdate#executeBatch()
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
