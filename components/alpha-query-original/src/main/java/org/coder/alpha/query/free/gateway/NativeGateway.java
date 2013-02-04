/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free.gateway;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.coder.alpha.jdbc.domain.TotalList;
import org.coder.alpha.jdbc.service.ModifyingRequest;
import org.coder.alpha.jdbc.service.QueryRequest;
import org.coder.alpha.jdbc.service.QueryService;
import org.coder.alpha.jdbc.service.ReadingRequest;
import org.coder.alpha.jdbc.strategy.RecordFilter;
import org.coder.alpha.query.EngineHints;
import org.coder.alpha.query.free.Conditions;
import org.coder.alpha.query.free.HitData;
import org.coder.alpha.query.free.ModifyingConditions;
import org.coder.alpha.query.free.ReadingConditions;



/**
 * The internal query for SQLEngine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class NativeGateway implements PersistenceGateway{
	
	/** the gateway of SQLEngine */
	private QueryService gateway;

	/**
	 * @param gateway the gateway to set
	 */
	public void setQueryService(QueryService gateway){
		this.gateway = gateway;
	}
	
	/**
	 * @see org.coder.alpha.query.free.gateway.PersistenceGateway#getTotalResult(org.coder.alpha.query.free.ReadingConditions)
	 */
	@Override
	public <T> HitData<T> getTotalResult(ReadingConditions param){
		TotalList<T> result = gateway.executeTotalQuery(createQueryParameter(param), getConnection(param));
		HitData<T> data = new HitData<T>(result.isLimited(),result.getHitCount());
		data.addAll(result);
		return data;
	}
	
	/**
	 * @see org.coder.alpha.query.free.gateway.PersistenceGateway#getFetchResult(org.coder.alpha.query.free.ReadingConditions)
	 */
	@Override
	public <T> List<T> getFetchResult(ReadingConditions param){
		return gateway.executeFetch(createQueryParameter(param), getConnection(param));		
	}
	
	/**
	 * @see org.coder.alpha.query.free.gateway.PersistenceGateway#count()
	 */
	@Override
	public long count(ReadingConditions param){
		return gateway.executeCount(createParameter(new ReadingRequest(),param), getConnection(param));
	}

	/**
	 * @see org.coder.alpha.query.free.gateway.PersistenceGateway#getResultList()
	 */
	@Override
	public <T> List<T> getResultList(ReadingConditions param){
		return gateway.executeQuery(createQueryParameter(param), getConnection(param));	
	}
	
	/**
	 * @see org.coder.alpha.query.free.gateway.PersistenceGateway#executeUpdate()
	 */
	@Override
	public int executeUpdate(ModifyingConditions param){
		return gateway.executeUpsert(createParameter(new ModifyingRequest(),param), getConnection(param));
	}
	
	/**
	 * @return the parameter
	 */
	private ReadingRequest createQueryParameter(final ReadingConditions param){
		ReadingRequest parameter = createParameter(new ReadingRequest(),param);
		parameter.setMaxSize(param.getMaxResults());
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
	private <S extends QueryRequest> S createParameter(S parameter,Conditions param){
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
	 * @see org.coder.alpha.query.free.gateway.PersistenceGateway#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<ModifyingConditions> param) {
		List<ModifyingRequest> engineParams = new ArrayList<ModifyingRequest>();
		for(ModifyingConditions p: param){
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
	private Connection getConnection(Conditions param){
		return param.getEntityManager().unwrap(Connection.class);
	}

}
