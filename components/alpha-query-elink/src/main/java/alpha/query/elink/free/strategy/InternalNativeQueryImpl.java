/**
 * Copyright 2011 the original author
 */
package alpha.query.elink.free.strategy;

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

import alpha.jdbc.domain.PreparedQuery;
import alpha.jdbc.domain.TotalData;
import alpha.jdbc.exception.ExceptionHandler;
import alpha.jdbc.service.ModifyingRequest;
import alpha.jdbc.service.QueryService;
import alpha.jdbc.service.impl.QueryServiceImpl;
import alpha.jdbc.strategy.QueryBuilder;
import alpha.jdbc.strategy.RecordFilter;
import alpha.jdbc.strategy.RecordHandlerFactory;
import alpha.jdbc.strategy.ResultSetHandler;
import alpha.jdbc.strategy.impl.RecordHandlerFactoryImpl;
import alpha.jdbc.strategy.impl.ResultSetHandlerImpl;
import alpha.query.elink.free.LazyList;
import alpha.query.elink.free.SQLExceptionHandlerImpl;
import alpha.query.free.Conditions;
import alpha.query.free.HitData;
import alpha.query.free.ModifyingConditions;
import alpha.query.free.ReadingConditions;
import alpha.query.free.ResultSetFilter;
import alpha.query.free.strategy.InternalQuery;



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
	
	/** the engine gateway */
	private QueryService queryService = new QueryServiceImpl();
	
	/**
	 * @param gateway the gateway to set
	 */
	public void setQueryService(QueryService queryService){
		this.queryService = queryService;
	}

	/**
	 * @param builder the builder to set
	 */
	public void setQueryBuilder(QueryBuilder builder){
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
	 * @see alpha.query.elink.free.strategy.InternalQuery#getResultList(alpha.query.elink.free.ReadingConditions)
	 */
	@Override
	public <T> List<T> getResultList(ReadingConditions parameter) {
		
		RecordFilter filter = createRecordFilter(parameter);
		Query query = mapping(parameter,createQuery(parameter));	
		if(parameter.getMaxSize() > 0){
			query.setMaxResults(parameter.getMaxSize());
		}		
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
	 * @see alpha.query.elink.internal.free.AbstractInternalQuery#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult(ReadingConditions parameter) {
		parameter.setMaxSize(1);
		List<T> result = getResultList(parameter);
		return result.isEmpty() ? null : result.get(0);
	}

	/**
	 * @see alpha.query.elink.free.strategy.InternalQuery#getTotalResult(alpha.query.elink.free.ReadingConditions)
	 */
	@Override
	public HitData getTotalResult(final ReadingConditions parameter) {

		RecordFilter filter = createRecordFilter(parameter);
		Query query = mapping(parameter,createQuery(parameter));		
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
	 * @see alpha.query.elink.elink.internal.free.AbstractInternalNativeQuery#getFetchResult()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getFetchResult(ReadingConditions parameter) {
		Query query = mapping(parameter,createQuery(parameter));	
		if(parameter.getMaxSize() > 0){
			query.setMaxResults(parameter.getMaxSize());
		}		
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		try{
			return new LazyList(cursor, recordHandlerFactory.create(parameter.getResultType(), cursor.getResultSet()),exceptionHandler,parameter.getFilter());
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
	protected Query createQuery(ReadingConditions param) {
		PreparedQuery preparedQuery = prepare(param);		
		Query query = param.getEntityManager().createNativeQuery(preparedQuery.getQueryStatement());
		return bindParmaeterToQuery(query, preparedQuery.getFirstList());			
	}
	
	/**
	 * Mapping the hint.
	 * 
	 * @param query the query
	 * @return the query
	 */
	protected Query mapping(ReadingConditions parameter,Query query){
				
		for(Map.Entry<String, Object> h : parameter.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}		
		if(parameter.getFirstResult() > 0){
			query.setFirstResult(parameter.getFirstResult());
		}
		//ResultSetの取得を可能とする。
		query.setHint(QueryHints.SCROLLABLE_CURSOR, HintValues.TRUE);
		return query;
	}
	
	/**
	 * @see alpha.query.elink.free.strategy.InternalQuery#count()
	 */
	@Override
	public long count(ReadingConditions param){		

		PreparedQuery preparedQuery = prepare(param);
		//countの場合は範囲設定無効とする。
		String executingSql = preparedQuery.getQueryStatement();
		Query query = param.getEntityManager().createNativeQuery(executingSql);
		query = bindParmaeterToQuery(query, preparedQuery.getFirstList());	
		
		for(Map.Entry<String, Object> h : param.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}
		Object value = query.getSingleResult();
		return Long.parseLong(value.toString());
	}
	
	/**
	 * @see alpha.query.elink.free.strategy.InternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate(ModifyingConditions param) {	
		PreparedQuery preparedQuery = prepare(param);
		Query query = param.getEntityManager().createNativeQuery(preparedQuery.getQueryStatement());
		query = bindParmaeterToQuery(query, preparedQuery.getFirstList());		
		
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
	protected Query createNamedQuery(String executingQuery,ReadingConditions param){
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
	protected PreparedQuery prepare(Conditions param){
		String str = param.getSql();	
		if(!param.isUseRowSql()){
			str = builder.build(param.getQueryId(), str);
			str = builder.evaluate(str, param.getParam(),param.getQueryId());
		}			
		return builder.prepare(str, Arrays.asList(param.getParam()),param.getWrappingClause(),param.getQueryId());			
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
	 * @see alpha.query.elink.free.strategy.InternalQuery#executeBatch(java.util.List)
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
