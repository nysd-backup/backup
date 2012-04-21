/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.free.strategy;

import java.util.List;

import kosmos.framework.sqlclient.ConnectionProvider;
import kosmos.framework.sqlclient.EngineHints;
import kosmos.framework.sqlclient.free.FreeParameter;
import kosmos.framework.sqlclient.free.FreeQueryParameter;
import kosmos.framework.sqlclient.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.free.NativeResult;
import kosmos.framework.sqlengine.executer.RecordFilter;
import kosmos.framework.sqlengine.facade.QueryParameter;
import kosmos.framework.sqlengine.facade.QueryResult;
import kosmos.framework.sqlengine.facade.SQLEngineFacade;
import kosmos.framework.sqlengine.facade.SQLParameter;
import kosmos.framework.sqlengine.facade.UpdateParameter;


/**
 * The internal query for SQLEngine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalNativeQueryImpl implements InternalQuery{
	
	/** the ConnectionProvider */
	private ConnectionProvider cs;
	
	/** the facade of SQLEngine */
	private SQLEngineFacade facade;
	
	/**
	 * @param cs the cs to set
	 */
	public void setConnectionProvider(ConnectionProvider cs){
		this.cs = cs;
	}
	
	/**
	 * @param facade the facade to set
	 */
	public void setSqlEngineFacade(SQLEngineFacade facade){
		this.facade = facade;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#getTotalResult(kosmos.framework.sqlclient.free.FreeQueryParameter)
	 */
	@Override
	public NativeResult getTotalResult(FreeQueryParameter param){
		QueryResult result = facade.executeTotalQuery(createQueryParameter(param), cs.getConnection());
		return new NativeResult(result.isLimited(), result.getResultList(), result.getHitCount());
	}
	
	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#getFetchResult(kosmos.framework.sqlclient.free.FreeQueryParameter)
	 */
	@Override
	public <T> List<T> getFetchResult(FreeQueryParameter param){
		return facade.executeFetch(createQueryParameter(param), cs.getConnection());		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#count()
	 */
	@Override
	public long count(FreeQueryParameter param){
		return facade.executeCount(createParameter(new QueryParameter(),param), cs.getConnection());
	}

	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#getResultList()
	 */
	@Override
	public <T> List<T> getResultList(FreeQueryParameter param){
		return facade.executeQuery(createQueryParameter(param), cs.getConnection());		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult(FreeQueryParameter param){
		param.setMaxSize(1);
		List<T> result = getResultList(param);
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}

	/**
	 * @see kosmos.framework.sqlclient.free.strategy.InternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate(FreeUpdateParameter param){
		return facade.executeUpdate(createParameter(new UpdateParameter(),param), cs.getConnection());
	}
	
	/**
	 * @return the parameter
	 */
	private QueryParameter createQueryParameter(final FreeQueryParameter param){
		QueryParameter parameter = createParameter(new QueryParameter(),param);		
		parameter.setMaxSize(param.getMaxSize());
		parameter.setFirstResult(param.getFirstResult());
		parameter.setResultType(param.getResultType());
		
		if(param.getHints().containsKey(EngineHints.SQLENGINE_JDBC_FETCHSIZE)){
			parameter.setFetchSize((Integer)param.getHints().get(EngineHints.SQLENGINE_JDBC_FETCHSIZE));
		}
		
		if(param.getFilter() != null){
			parameter.setFilter(
				new RecordFilter(){
					@Override
					public <T> T edit(T data) {
						return param.getFilter().edit(data);
					}					
				}
			);
		}
		return parameter;
	}
	
	/**
	 * Creates the baseParameter.
	 * @param parameter the parameter
	 * @return the result
	 */
	private <S extends SQLParameter> S createParameter(S parameter,FreeParameter param){
		parameter.setSqlId(param.getQueryId());
		parameter.setSql(param.getSql());		

		if(param.getHints().containsKey(EngineHints.SQLENGINE_JDBC_TIMEOUT)){
			parameter.setTimeoutSeconds((Integer)param.getHints().get(EngineHints.SQLENGINE_JDBC_TIMEOUT));
		}
		parameter.setAllBranchParameter(param.getBranchParam());
		parameter.setUseRowSql(param.isUseRowSql());
		parameter.setAllParameter(param.getParam());
		return parameter;
	}

}
