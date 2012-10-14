/**
 * Copyright 2011 the original author
 */
package alpha.sqlclient.free.strategy;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import alpha.jdbc.domain.TotalData;
import alpha.jdbc.service.ModifyingRequest;
import alpha.jdbc.service.QueryRequest;
import alpha.jdbc.service.QueryService;
import alpha.jdbc.service.ReadingRequest;
import alpha.jdbc.strategy.RecordFilter;
import alpha.sqlclient.EngineHints;
import alpha.sqlclient.free.FreeModifyQueryParameter;
import alpha.sqlclient.free.FreeQueryParameter;
import alpha.sqlclient.free.FreeReadQueryParameter;
import alpha.sqlclient.free.HitData;
import alpha.sqlclient.free.strategy.InternalQuery;


/**
 * The internal query for SQLEngine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalNativeQueryImpl implements InternalQuery{
	
	/** the gateway of SQLEngine */
	private QueryService gateway;

	/**
	 * @param gateway the gateway to set
	 */
	public void setQueryService(QueryService gateway){
		this.gateway = gateway;
	}
	
	/**
	 * @see alpha.sqlclient.free.strategy.InternalQuery#getTotalResult(alpha.sqlclient.free.FreeReadQueryParameter)
	 */
	@Override
	public HitData getTotalResult(FreeReadQueryParameter param){
		TotalData result = gateway.executeTotalQuery(createQueryParameter(param), getConnection(param));
		return new HitData(result.isLimited(), result.getResultList(), result.getHitCount());
	}
	
	/**
	 * @see alpha.sqlclient.free.strategy.InternalQuery#getFetchResult(alpha.sqlclient.free.FreeReadQueryParameter)
	 */
	@Override
	public <T> List<T> getFetchResult(FreeReadQueryParameter param){
		return gateway.executeFetch(createQueryParameter(param), getConnection(param));		
	}
	
	/**
	 * @see alpha.sqlclient.free.strategy.InternalQuery#count()
	 */
	@Override
	public long count(FreeReadQueryParameter param){
		return gateway.executeCount(createParameter(new ReadingRequest(),param), getConnection(param));
	}

	/**
	 * @see alpha.sqlclient.free.strategy.InternalQuery#getResultList()
	 */
	@Override
	public <T> List<T> getResultList(FreeReadQueryParameter param){
		return gateway.executeQuery(createQueryParameter(param), getConnection(param));	
	}
	
	/**
	 * @see alpha.sqlclient.free.strategy.InternalQuery#getSingleResult()
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
	 * @see alpha.sqlclient.free.strategy.InternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate(FreeModifyQueryParameter param){
		return gateway.executeUpsert(createParameter(new ModifyingRequest(),param), getConnection(param));
	}
	
	/**
	 * @return the parameter
	 */
	private ReadingRequest createQueryParameter(final FreeReadQueryParameter param){
		ReadingRequest parameter = createParameter(new ReadingRequest(),param);
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
	private <S extends QueryRequest> S createParameter(S parameter,FreeQueryParameter param){
		parameter.setSqlId(param.getQueryId());
		parameter.setSql(param.getSql());	
		parameter.setWrappingClause(param.getWrappingClause());

		if(param.getHints().containsKey(EngineHints.SQLENGINE_JDBC_TIMEOUT)){
			parameter.setTimeoutSeconds((Integer)param.getHints().get(EngineHints.SQLENGINE_JDBC_TIMEOUT));
		}	
		parameter.setUseRowSql(param.isUseRowSql());
		parameter.setAllParameter(param.getParam());
		return parameter;
	}

	/**
	 * @see alpha.sqlclient.free.strategy.InternalQuery#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<FreeModifyQueryParameter> param) {
		List<ModifyingRequest> engineParams = new ArrayList<ModifyingRequest>();
		for(FreeQueryParameter p: param){
			ModifyingRequest ep = new ModifyingRequest();
			ep.setAllParameter(p.getParam());
			ep.setSqlId(p.getQueryId());
			ep.setSql(p.getSql());
			ep.setUseRowSql(p.isUseRowSql());		
			engineParams.add(ep);
		}				
		return gateway.executeBatch(engineParams, getConnection(param.get(0)));
	}
	
	/**
	 * @param param
	 * @return
	 */
	private Connection getConnection(FreeQueryParameter param){
		return param.getEntityManager().unwrap(Connection.class);
	}

}
