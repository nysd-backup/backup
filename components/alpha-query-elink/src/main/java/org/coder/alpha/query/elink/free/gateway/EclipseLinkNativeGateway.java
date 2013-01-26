/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.elink.free.gateway;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.coder.alpha.jdbc.domain.PreparedQuery;
import org.coder.alpha.jdbc.domain.TotalData;
import org.coder.alpha.jdbc.exception.ExceptionHandler;
import org.coder.alpha.jdbc.service.ModifyingRequest;
import org.coder.alpha.jdbc.service.QueryService;
import org.coder.alpha.jdbc.service.impl.QueryServiceImpl;
import org.coder.alpha.jdbc.strategy.QueryLoader;
import org.coder.alpha.jdbc.strategy.RecordFilter;
import org.coder.alpha.jdbc.strategy.RecordHandlerFactory;
import org.coder.alpha.jdbc.strategy.ResultSetHandler;
import org.coder.alpha.jdbc.strategy.impl.RecordHandlerFactoryImpl;
import org.coder.alpha.jdbc.strategy.impl.ResultSetHandlerImpl;
import org.coder.alpha.query.elink.free.LazyList;
import org.coder.alpha.query.elink.free.SQLExceptionHandlerImpl;
import org.coder.alpha.query.free.Conditions;
import org.coder.alpha.query.free.HitData;
import org.coder.alpha.query.free.ModifyingConditions;
import org.coder.alpha.query.free.ReadingConditions;
import org.coder.alpha.query.free.ResultSetFilter;
import org.coder.alpha.query.free.gateway.PersistenceGateway;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.ScrollableCursor;



/**
 * An internal query using EclipseLink.
 * 
 * <pre>
 * 	Gets the result binded not to EntiyBean but to POJO.
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
public class EclipseLinkNativeGateway implements PersistenceGateway {
		
	/** the <code>QueryLoader</code> */
	private QueryLoader loader;

	/** the ResultSetHandler */
	private ResultSetHandler handler = new ResultSetHandlerImpl();
	
	/** the RecordHandlerFactory */
	private RecordHandlerFactory recordHandlerFactory = new RecordHandlerFactoryImpl();
	
	/** the ExceptionHandler */
	private ExceptionHandler exceptionHandler = new SQLExceptionHandlerImpl();
	
	/** the engine gateway */
	private QueryService queryService = new QueryServiceImpl();
	
	/**
	 * @param gateway the gateway to set
	 */
	public void setQueryService(QueryService queryService){
		this.queryService = queryService;
	}

	/**
	 * @param loader the loader to set
	 */
	public void setQueryLoader(QueryLoader loader){
		this.loader = loader;
	}
	
	/**
	 * @param exceptionHandler the exceptionHandler to set
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler){
		this.exceptionHandler = exceptionHandler;
	}
	
	/**
	 * @param handler the handler to set
	 */
	public void setResultSetHandler(ResultSetHandler handler){
		this.handler = handler;
	}
	
	/**
	 * @param handler the recordHandlerFactory to set
	 */
	public void setRecordHandlerFactory(RecordHandlerFactory recordHandlerFactory){
		this.recordHandlerFactory = recordHandlerFactory;
	}

	/**
	 * @see org.coder.alpha.query.elink.free.gateway.PersistenceGateway#getResultList(org.coder.alpha.query.elink.free.ReadingConditions)
	 */
	@Override
	public <T> List<T> getResultList(ReadingConditions parameter) {
		
		RecordFilter filter = createRecordFilter(parameter);
		Query query = setRangeAndCursor(parameter.getFirstResult(),parameter.getMaxSize(),createQuery(parameter));	
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();	
		try {			
			return handler.getResultList(cursor.getResultSet(), parameter.getResultType(), filter);
		}catch (SQLException e) {
			throw exceptionHandler.rethrow(e);
		}finally{
			cursor.close();
		}
	}

	/**
	 * @see org.coder.alpha.query.elink.free.gateway.PersistenceGateway#getTotalResult(org.coder.alpha.query.elink.free.ReadingConditions)
	 */
	@Override
	public HitData getTotalResult(final ReadingConditions parameter) {

		RecordFilter filter = createRecordFilter(parameter);
		Query query = setRangeAndCursor(parameter.getFirstResult(),0,createQuery(parameter));		
		TotalData result;
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();		
		try {		
			ResultSet rs = cursor.getResultSet();
			result = handler.getResultList(rs, parameter.getResultType(),filter,parameter.getMaxSize());
		}catch (SQLException e) {
			throw exceptionHandler.rethrow(e);
		}finally{
			cursor.close();
		}
		return new HitData(result.isLimited(),result.getResultList(),result.getHitCount());
		
	}
	
	/**
	 * @see org.coder.alpha.query.elink.elink.internal.free.AbstractInternalNativeQuery#getFetchResult()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getFetchResult(ReadingConditions parameter) {
		Query query = setRangeAndCursor(parameter.getFirstResult(),parameter.getMaxSize(),createQuery(parameter));	
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		try{
			return new LazyList(cursor, recordHandlerFactory.create(parameter.getResultType(), cursor.getResultSet()),exceptionHandler,parameter.getFilter());
		} catch (SQLException e) {
			cursor.close();
			throw exceptionHandler.rethrow(e);
		}	
	}
	
	/**
	 * @see org.coder.alpha.query.elink.free.gateway.PersistenceGateway#count()
	 */
	@Override
	public long count(ReadingConditions param){		
		Query query = createQuery(param);
		Object value = query.getSingleResult();
		return Long.parseLong(value.toString());
	}
	
	/**
	 * @see org.coder.alpha.query.elink.free.gateway.PersistenceGateway#executeUpdate()
	 */
	@Override
	public int executeUpdate(ModifyingConditions param) {	
		return createQuery(param).executeUpdate();
	}
	

	/**
	 * Creates the query.
	 * 
	 * @return the query
	 */
	@SuppressWarnings("unchecked")
	protected Query createQuery(Conditions param) {
		
		//build the sql
		String str = param.getSql();	
		if(!param.isUseRowSql()){
			str = loader.build(param.getQueryId(), str);
			str = loader.evaluate(str, param.getParam(),param.getQueryId());
		}			
		PreparedQuery preparedQuery = loader.prepare(str, Arrays.asList(param.getParam()),param.getWrappingClause(),param.getQueryId());			
		
		Query query = param.getEntityManager().createNativeQuery(preparedQuery.getQueryStatement());
		
		//hints
		for(Map.Entry<String, Object> h : param.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}	
		//parameter
		for(int i=0; i < preparedQuery.getFirstList().size(); i++){			
			query.setParameter(i+1,preparedQuery.getFirstList().get(i));			
		}		
		return query;
	}
	
	/**
	 * Mapping the hint.
	 * 
	 * @param query the query
	 * @return the query
	 */
	protected Query setRangeAndCursor(int firstResult , int maxSize,Query query){

		if(firstResult > 0){
			query.setFirstResult(firstResult);
		}
		if(maxSize > 0){
			query.setMaxResults(maxSize);
		}		
		//ResultSetの取得を可能とする。
		query.setHint(QueryHints.SCROLLABLE_CURSOR, HintValues.TRUE);
		return query;
	}
	
	/**
	 * @param parameter
	 * @return
	 */
	private RecordFilter createRecordFilter(ReadingConditions parameter){
		final ResultSetFilter filter = parameter.getFilter();
		RecordFilter recordFilter = null;
		if(filter != null){
			recordFilter = new RecordFilter(){
				@Override
				public <K> K edit(K data) {
					return filter.edit(data);
				}					
			};
		}			
		return recordFilter;
	}

	/**
	 * @see org.coder.alpha.query.elink.free.gateway.PersistenceGateway#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<ModifyingConditions> param) {
		
		//TODO 要検証		
		
		List<ModifyingRequest> engineParams = new ArrayList<ModifyingRequest>();
		for(Conditions p: param){
			ModifyingRequest ep = new ModifyingRequest();
			ep.setAllParameter(p.getParam());		
			ep.setSqlId(p.getQueryId());
			ep.setSql(p.getSql());
			ep.setUseRowSql(p.isUseRowSql());	
			Object value = p.getHints().get(QueryHints.JDBC_TIMEOUT);
			if( value != null){
				ep.setTimeoutSeconds((Integer)value);
			}
			engineParams.add(ep);
		}		
		
		Connection con = param.get(0).getEntityManager().unwrap(Connection.class);
		
		return queryService.executeBatch(engineParams, con);
		
	}

}
