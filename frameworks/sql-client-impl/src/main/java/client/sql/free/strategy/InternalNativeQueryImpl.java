/**
 * Copyright 2011 the original author
 */
package client.sql.free.strategy;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import sqlengine.executer.RecordFilter;
import sqlengine.facade.QueryExecutor;
import sqlengine.facade.QueryParameter;
import sqlengine.facade.QueryResult;
import sqlengine.facade.SelectParameter;
import sqlengine.facade.UpsertParameter;
import client.sql.EngineHints;
import client.sql.free.FreeModifyQueryParameter;
import client.sql.free.FreeQueryParameter;
import client.sql.free.FreeReadQueryParameter;
import client.sql.free.NativeResult;



/**
 * The internal query for SQLEngine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalNativeQueryImpl implements InternalQuery{
	
	/** the facade of SQLEngine */
	private QueryExecutor facade;

	/**
	 * @param facade the facade to set
	 */
	public void setQueryExecutor(QueryExecutor facade){
		this.facade = facade;
	}
	
	/**
	 * @see client.sql.free.strategy.InternalQuery#getTotalResult(client.sql.free.FreeReadQueryParameter)
	 */
	@Override
	public NativeResult getTotalResult(FreeReadQueryParameter param){
		QueryResult result = facade.executeTotalQuery(createQueryParameter(param), getConnection(param));
		return new NativeResult(result.isLimited(), result.getResultList(), result.getHitCount());
	}
	
	/**
	 * @see client.sql.free.strategy.InternalQuery#getFetchResult(client.sql.free.FreeReadQueryParameter)
	 */
	@Override
	public <T> List<T> getFetchResult(FreeReadQueryParameter param){
		return facade.executeFetch(createQueryParameter(param), getConnection(param));		
	}
	
	/**
	 * @see client.sql.free.strategy.InternalQuery#count()
	 */
	@Override
	public long count(FreeReadQueryParameter param){
		return facade.executeCount(createParameter(new SelectParameter(),param), getConnection(param));
	}

	/**
	 * @see client.sql.free.strategy.InternalQuery#getResultList()
	 */
	@Override
	public <T> List<T> getResultList(FreeReadQueryParameter param){
		return facade.executeQuery(createQueryParameter(param), getConnection(param));	
	}
	
	/**
	 * @see client.sql.free.strategy.InternalQuery#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult(FreeReadQueryParameter param){
		param.setMaxSize(1);
		List<T> result = getResultList(param);
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}

	/**
	 * @see client.sql.free.strategy.InternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate(FreeModifyQueryParameter param){
		return facade.executeUpsert(createParameter(new UpsertParameter(),param), getConnection(param));
	}
	
	/**
	 * @return the parameter
	 */
	private SelectParameter createQueryParameter(final FreeReadQueryParameter param){
		SelectParameter parameter = createParameter(new SelectParameter(),param);
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
	private <S extends QueryParameter> S createParameter(S parameter,FreeQueryParameter param){
		parameter.setSqlId(param.getQueryId());
		parameter.setSql(param.getSql());		

		if(param.getHints().containsKey(EngineHints.SQLENGINE_JDBC_TIMEOUT)){
			parameter.setTimeoutSeconds((Integer)param.getHints().get(EngineHints.SQLENGINE_JDBC_TIMEOUT));
		}	
		parameter.setUseRowSql(param.isUseRowSql());
		parameter.setAllParameter(param.getParam());
		return parameter;
	}

	/**
	 * @see client.sql.free.strategy.InternalQuery#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<FreeModifyQueryParameter> param) {
		List<UpsertParameter> engineParams = new ArrayList<UpsertParameter>();
		for(FreeQueryParameter p: param){
			UpsertParameter ep = new UpsertParameter();
			ep.setAllParameter(p.getParam());
			ep.setSqlId(p.getQueryId());
			ep.setSql(p.getSql());
			ep.setUseRowSql(p.isUseRowSql());		
			engineParams.add(ep);
		}				
		return facade.executeBatch(engineParams, getConnection(param.get(0)));
	}
	
	/**
	 * @param param
	 * @return
	 */
	private Connection getConnection(FreeQueryParameter param){
		return param.getEntityManager().unwrap(Connection.class);
	}

}
