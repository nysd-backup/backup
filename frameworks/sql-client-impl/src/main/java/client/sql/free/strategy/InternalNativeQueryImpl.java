/**
 * Copyright 2011 the original author
 */
package client.sql.free.strategy;

import java.util.ArrayList;
import java.util.List;

import client.sql.ConnectionProvider;
import client.sql.EngineHints;
import client.sql.free.FreeQueryParameter;
import client.sql.free.FreeSelectParameter;
import client.sql.free.FreeUpsertParameter;
import client.sql.free.NativeResult;
import client.sql.free.strategy.InternalQuery;

import sqlengine.executer.RecordFilter;
import sqlengine.facade.QueryParameter;
import sqlengine.facade.QueryResult;
import sqlengine.facade.SQLEngineFacade;
import sqlengine.facade.SQLParameter;
import sqlengine.facade.UpdateParameter;



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
	 * @see client.sql.free.strategy.InternalQuery#getTotalResult(client.sql.free.FreeSelectParameter)
	 */
	@Override
	public NativeResult getTotalResult(FreeSelectParameter param){
		QueryResult result = facade.executeTotalQuery(createQueryParameter(param), cs.getConnection());
		return new NativeResult(result.isLimited(), result.getResultList(), result.getHitCount());
	}
	
	/**
	 * @see client.sql.free.strategy.InternalQuery#getFetchResult(client.sql.free.FreeSelectParameter)
	 */
	@Override
	public <T> List<T> getFetchResult(FreeSelectParameter param){
		return facade.executeFetch(createQueryParameter(param), cs.getConnection());		
	}
	
	/**
	 * @see client.sql.free.strategy.InternalQuery#count()
	 */
	@Override
	public long count(FreeSelectParameter param){
		return facade.executeCount(createParameter(new QueryParameter(),param), cs.getConnection());
	}

	/**
	 * @see client.sql.free.strategy.InternalQuery#getResultList()
	 */
	@Override
	public <T> List<T> getResultList(FreeSelectParameter param){
		return facade.executeQuery(createQueryParameter(param), cs.getConnection());		
	}
	
	/**
	 * @see client.sql.free.strategy.InternalQuery#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult(FreeSelectParameter param){
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
	public int executeUpdate(FreeUpsertParameter param){
		return facade.executeUpdate(createParameter(new UpdateParameter(),param), cs.getConnection());
	}
	
	/**
	 * @return the parameter
	 */
	private QueryParameter createQueryParameter(final FreeSelectParameter param){
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
	private <S extends SQLParameter> S createParameter(S parameter,FreeQueryParameter param){
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

	/**
	 * @see client.sql.free.strategy.InternalQuery#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<FreeUpsertParameter> param) {
		List<UpdateParameter> engineParams = new ArrayList<UpdateParameter>();
		for(FreeQueryParameter p: param){
			UpdateParameter ep = new UpdateParameter();
			ep.setAllParameter(p.getParam());
			ep.setAllBranchParameter(p.getBranchParam());
			ep.setSqlId(p.getQueryId());
			ep.setSql(p.getSql());
			ep.setUseRowSql(p.isUseRowSql());		
			engineParams.add(ep);
		}				
		return facade.executeBatch(engineParams, cs.getConnection());
	}

}
