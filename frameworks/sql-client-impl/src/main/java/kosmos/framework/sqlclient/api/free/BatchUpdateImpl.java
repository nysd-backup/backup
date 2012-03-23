/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import java.util.ArrayList;
import java.util.List;

import kosmos.framework.sqlclient.api.ConnectionProvider;
import kosmos.framework.sqlclient.api.orm.PersistenceHints;
import kosmos.framework.sqlclient.api.wrapper.free.AbstractNativeUpdate;
import kosmos.framework.sqlengine.facade.SQLEngineFacade;
import kosmos.framework.sqlengine.facade.UpdateParameter;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class BatchUpdateImpl implements BatchUpdate{
	
	/** parameters */
	private List<FreeParameter> parameters = new ArrayList<FreeParameter>();
	
	/** the SQLEngineFacade */
	private SQLEngineFacade facade;
	
	/** the ConnectionProvider */
	private ConnectionProvider provider;
	
	/**
	 * @param facade
	 * @param provider
	 */
	public BatchUpdateImpl(SQLEngineFacade facade,ConnectionProvider provider){
		this.facade = facade;
		this.provider = provider;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.BatchUpdate#addBatch(kosmos.framework.sqlclient.api.free.NativeUpdate)
	 */
	@Override
	public void addBatch(FreeUpdateParameter parameter) {
		parameters.add(parameter);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.BatchUpdate#addBatch(kosmos.framework.sqlclient.api.free.NativeUpdate)
	 */
	@Override
	public void addBatch(NativeUpdate parameter) {
		addBatch(parameter.getCurrentParams());
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.BatchUpdate#addBatch(kosmos.framework.sqlclient.api.wrapper.free.AbstractNativeUpdate)
	 */
	@Override
	public void addBatch(AbstractNativeUpdate parameter) {
		addBatch(parameter.unwrap());
	}


	/**
	 * @see kosmos.framework.sqlclient.api.free.BatchUpdate#executeBatch()
	 */
	@Override
	public int[] executeBatch() {
		try{
			List<UpdateParameter> engineParams = new ArrayList<UpdateParameter>();
			for(FreeParameter p: parameters){
				UpdateParameter ep = new UpdateParameter();
				ep.setAllParameter(p.getParam());
				ep.setAllBranchParameter(p.getBranchParam());
				ep.setSqlId(p.getQueryId());
				ep.setSql(p.getSql());
				ep.setUseRowSql(p.isUseRowSql());
				if(p.getHints().containsKey(PersistenceHints.SQLENGINE_JDBC_TIMEOUT)){
					ep.setTimeoutSeconds((Integer)p.getHints().get(PersistenceHints.SQLENGINE_JDBC_TIMEOUT));
				}		
				engineParams.add(ep);
			}
			return facade.executeBatch(engineParams, provider.getConnection());
		}finally{
			parameters.clear();
		}
	}

}
