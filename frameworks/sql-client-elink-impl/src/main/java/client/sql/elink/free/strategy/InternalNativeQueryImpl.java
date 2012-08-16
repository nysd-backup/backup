/**
 * Copyright 2011 the original author
 */
package client.sql.elink.free.strategy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.ScrollableCursor;

import sqlengine.builder.PreparedQuery;
import sqlengine.builder.QueryBuilder;
import sqlengine.exception.ExceptionHandler;
import sqlengine.executer.RecordFilter;
import sqlengine.executer.RecordHandlerFactory;
import sqlengine.executer.ResultSetHandler;
import sqlengine.executer.impl.RecordHandlerFactoryImpl;
import sqlengine.executer.impl.ResultSetHandlerImpl;
import sqlengine.facade.QueryExecutor;
import sqlengine.facade.QueryResult;
import sqlengine.facade.UpsertParameter;
import sqlengine.facade.impl.QueryExecutorImpl;
import client.sql.elink.free.LazyList;
import client.sql.elink.free.SQLExceptionHandlerImpl;
import client.sql.free.FreeModifyQueryParameter;
import client.sql.free.FreeQueryParameter;
import client.sql.free.FreeReadQueryParameter;
import client.sql.free.NativeResult;
import client.sql.free.ResultSetFilter;
import client.sql.free.strategy.InternalQuery;


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
public class InternalNativeQueryImpl implements InternalQuery {
		
	/** the <code>QueryBuilder</code> */
	private QueryBuilder builder;

	/** the ResultSetHandler */
	private ResultSetHandler handler = new ResultSetHandlerImpl();
	
	/** the RecordHandlerFactory */
	private RecordHandlerFactory recordHandlerFactory = new RecordHandlerFactoryImpl();
	
	/** the ExceptionHandler */
	private ExceptionHandler exceptionHandler = new SQLExceptionHandlerImpl();
	
	/** the engine facade */
	private QueryExecutor queryExecutor = new QueryExecutorImpl();
	
	/**
	 * @param facade the facade to set
	 */
	public void setQueryExecutor(QueryExecutor queryExecutor){
		this.queryExecutor = queryExecutor;
	}

	/**
	 * @param builder the builder to set
	 */
	public void setSqlBuilder(QueryBuilder builder){
		this.builder = builder;
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
	 * @see client.sql.free.strategy.InternalQuery#getResultList(client.sql.free.FreeReadQueryParameter)
	 */
	@Override
	public <T> List<T> getResultList(FreeReadQueryParameter parameter) {
		
		RecordFilter filter = createRecordFilter(parameter);
		Query query = mapping(parameter,createQuery(parameter));	
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
	 * @see client.sql.internal.free.AbstractInternalQuery#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult(FreeReadQueryParameter parameter) {
		parameter.setMaxSize(1);
		List<T> result = getResultList(parameter);
		return result.isEmpty() ? null : result.get(0);
	}

	/**
	 * @see client.sql.free.strategy.InternalQuery#getTotalResult(client.sql.free.FreeReadQueryParameter)
	 */
	@Override
	public NativeResult getTotalResult(final FreeReadQueryParameter parameter) {

		RecordFilter filter = createRecordFilter(parameter);
		Query query = mapping(parameter,createQuery(parameter));
		query.setMaxResults(0);
		
		QueryResult result;
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();		
		try {		
			ResultSet rs = cursor.getResultSet();
			result = handler.getResultList(rs, parameter.getResultType(),filter,parameter.getMaxSize());
		}catch (SQLException e) {
			throw exceptionHandler.rethrow(e);
		}finally{
			cursor.close();
		}
		return new NativeResult(result.isLimited(),result.getResultList(),result.getHitCount());
		
	}
	
	/**
	 * @see client.sql.elink.internal.free.AbstractInternalNativeQuery#getFetchResult()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getFetchResult(FreeReadQueryParameter parameter) {
		Query query = mapping(parameter,createQuery(parameter));		
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		try{
			return new LazyList(cursor, recordHandlerFactory.create(parameter.getResultType(), cursor.getResultSet()),exceptionHandler);
		} catch (SQLException e) {
			cursor.close();
			throw exceptionHandler.rethrow(e);
		}	
	}
	
	/**
	 * Creates the query.
	 * 
	 * @return the query
	 */
	protected Query createQuery(FreeReadQueryParameter param) {
		PreparedQuery preparedQuery = prepare(param);		
		Query query = param.getName() != null ? createNamedQuery(preparedQuery.getStatement(), param) : param.getEntityManager().createNativeQuery(preparedQuery.getStatement());
		return bindParmaeterToQuery(query, preparedQuery.getFirstList());			
	}
	
	/**
	 * Mapping the hint.
	 * 
	 * @param query the query
	 * @return the query
	 */
	protected Query mapping(FreeReadQueryParameter parameter,Query query){
				
		for(Map.Entry<String, Object> h : parameter.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}		
		if(parameter.getFirstResult() > 0){
			query.setFirstResult(parameter.getFirstResult());
		}
		if(parameter.getMaxSize() > 0){
			query.setMaxResults(parameter.getMaxSize());
		}
		
		//ResultSetの取得を可能とする。
		query.setHint(QueryHints.SCROLLABLE_CURSOR, HintValues.TRUE);
		return query;
	}
	
	/**
	 * @see client.sql.free.strategy.InternalQuery#count()
	 */
	@Override
	public long count(FreeReadQueryParameter param){		

		PreparedQuery preparedQuery = prepare(param);
		
		//countの場合は範囲設定無効とする。
		String executingSql = String.format("select count(*) from (%s)",preparedQuery.getStatement());
		Query query = param.getName() != null ? createNamedQuery(executingSql,param) : param.getEntityManager().createNativeQuery(executingSql);
		query = bindParmaeterToQuery(query, preparedQuery.getFirstList());	
		
		for(Map.Entry<String, Object> h : param.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}
		Object value = query.getSingleResult();
		return Long.parseLong(value.toString());
	}
	
	/**
	 * @see client.sql.free.strategy.InternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate(FreeModifyQueryParameter param) {	
		Query query = null;
		if(param.getName() != null){
			query = param.getEntityManager().createNamedQuery(param.getName());	
			for(Map.Entry<String, Object> p : param.getParam().entrySet()){
				query.setParameter(p.getKey(),p.getValue());
			}
		}else {
			PreparedQuery preparedQuery = prepare(param);
			query = param.getEntityManager().createNativeQuery(preparedQuery.getStatement());
			query = bindParmaeterToQuery(query, preparedQuery.getFirstList());		
		}			
		
		for(Map.Entry<String, Object> h : param.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}	

		return query.executeUpdate();
	}
	

	/**
	 * Creates the named query.
	 * 
	 * @return the query
	 */
	protected Query createNamedQuery(String executingQuery,FreeReadQueryParameter param){
		Query query = param.getEntityManager().createNamedQuery(executingQuery);
		for(Map.Entry<String, Object> h : param.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}		
		if(param.getFirstResult() > 0){
			query.setFirstResult(param.getFirstResult());
		}
		if(param.getMaxSize() > 0){
			query.setMaxResults(param.getMaxSize());
		}
		return query;
	}
	
	/**
	 * Builds the SQL.
	 * 
	 * @param bindList the bind parameters
	 * @return the SQL
	 */
	@SuppressWarnings("unchecked")
	protected PreparedQuery prepare(FreeQueryParameter param){
		String str = param.getSql();	
		if(!param.isUseRowSql()){
			str = builder.build(param.getQueryId(), str);
			str = builder.evaluate(str, param.getParam(),param.getQueryId());
		}			
		return builder.prepare(str, Arrays.asList(param.getParam()),param.getQueryId());			
	}
	
	/**
	 * Bind the parameter to query.
	 * 
	 * @param query the query
	 * @param bindList the bind parameters
	 * @return the query
	 */
	protected Query bindParmaeterToQuery(Query query,List<Object> bindList){
		
		for(int i=0; i < bindList.size(); i++){			
			query.setParameter(i+1,bindList.get(i));			
		}		
		return query;
	}
	
	/**
	 * @param parameter
	 * @return
	 */
	private RecordFilter createRecordFilter(FreeReadQueryParameter parameter){
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
	 * @see client.sql.free.strategy.InternalQuery#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<FreeModifyQueryParameter> param) {
		
		//TODO 要検証		
		
		List<UpsertParameter> engineParams = new ArrayList<UpsertParameter>();
		for(FreeQueryParameter p: param){
			UpsertParameter ep = new UpsertParameter();
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
		
		return queryExecutor.executeBatch(engineParams, con);
		
	}

}
