/**
 * Copyright 2011 the original author
 */
package alpha.sqlclient.elink.free.strategy;

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
import alpha.sqlclient.elink.free.LazyList;
import alpha.sqlclient.elink.free.SQLExceptionHandlerImpl;
import alpha.sqlclient.free.FreeModifyQueryParameter;
import alpha.sqlclient.free.FreeQueryParameter;
import alpha.sqlclient.free.FreeReadQueryParameter;
import alpha.sqlclient.free.HitData;
import alpha.sqlclient.free.ResultSetFilter;
import alpha.sqlclient.free.strategy.InternalQuery;



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
	 * @see alpha.sqlclient.elink.free.strategy.InternalQuery#getResultList(alpha.sqlclient.elink.free.FreeReadQueryParameter)
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
	 * @see alpha.sqlclient.elink.internal.free.AbstractInternalQuery#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult(FreeReadQueryParameter parameter) {
		parameter.setMaxSize(1);
		List<T> result = getResultList(parameter);
		return result.isEmpty() ? null : result.get(0);
	}

	/**
	 * @see alpha.sqlclient.elink.free.strategy.InternalQuery#getTotalResult(alpha.sqlclient.elink.free.FreeReadQueryParameter)
	 */
	@Override
	public HitData getTotalResult(final FreeReadQueryParameter parameter) {

		RecordFilter filter = createRecordFilter(parameter);
		Query query = mapping(parameter,createQuery(parameter));
		query.setMaxResults(0);
		
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
	 * @see alpha.sqlclient.elink.elink.internal.free.AbstractInternalNativeQuery#getFetchResult()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getFetchResult(FreeReadQueryParameter parameter) {
		Query query = mapping(parameter,createQuery(parameter));		
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
	protected Query createQuery(FreeReadQueryParameter param) {
		PreparedQuery preparedQuery = prepare(param);		
		Query query = param.getName() != null ? createNamedQuery(preparedQuery.getQueryStatement(), param) : param.getEntityManager().createNativeQuery(preparedQuery.getQueryStatement());
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
	 * @see alpha.sqlclient.elink.free.strategy.InternalQuery#count()
	 */
	@Override
	public long count(FreeReadQueryParameter param){		

		PreparedQuery preparedQuery = prepare(param);
		//countの場合は範囲設定無効とする。
		String executingSql = preparedQuery.getQueryStatement();
		Query query = param.getName() != null ? createNamedQuery(executingSql,param) : param.getEntityManager().createNativeQuery(executingSql);
		query = bindParmaeterToQuery(query, preparedQuery.getFirstList());	
		
		for(Map.Entry<String, Object> h : param.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}
		Object value = query.getSingleResult();
		return Long.parseLong(value.toString());
	}
	
	/**
	 * @see alpha.sqlclient.elink.free.strategy.InternalQuery#executeUpdate()
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
			query = param.getEntityManager().createNativeQuery(preparedQuery.getQueryStatement());
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
	 * @see alpha.sqlclient.elink.free.strategy.InternalQuery#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<FreeModifyQueryParameter> param) {
		
		//TODO 要検証		
		
		List<ModifyingRequest> engineParams = new ArrayList<ModifyingRequest>();
		for(FreeQueryParameter p: param){
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
