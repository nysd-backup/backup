/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.free;

import java.util.ArrayList;
import java.util.List;

import kosmos.framework.sqlclient.ConnectionProvider;
import kosmos.framework.sqlclient.EngineHints;
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
			List<UpdateParameter> engineParams = new ArrayList<UpdateParameter>();
			for(FreeParameter p: parameters){
				UpdateParameter ep = new UpdateParameter();
				ep.setAllParameter(p.getParam());
				ep.setAllBranchParameter(p.getBranchParam());
				ep.setSqlId(p.getQueryId());
				ep.setSql(p.getSql());
				ep.setUseRowSql(p.isUseRowSql());
				if(p.getHints().containsKey(EngineHints.SQLENGINE_JDBC_TIMEOUT)){
					ep.setTimeoutSeconds((Integer)p.getHints().get(EngineHints.SQLENGINE_JDBC_TIMEOUT));
				}		
				engineParams.add(ep);
			}
			return facade.executeBatch(engineParams, provider.getConnection());
		}finally{
			parameters.clear();
		}
	}

}
