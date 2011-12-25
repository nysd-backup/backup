/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.free.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import kosmos.framework.jpqlclient.internal.free.AbstractInternalJpaNativeQuery;
import kosmos.framework.jpqlclient.internal.free.LazyList;
import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.internal.free.impl.DelegatingResultSetFilter;
import kosmos.framework.sqlengine.builder.SQLBuilder;
import kosmos.framework.sqlengine.exception.ExceptionHandler;
import kosmos.framework.sqlengine.executer.RecordHandlerFactory;
import kosmos.framework.sqlengine.executer.ResultSetHandler;
import kosmos.framework.sqlengine.facade.QueryResult;

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
public class InternalEclipseLinkNativeQueryImpl extends AbstractInternalJpaNativeQuery{

	/** the ResultSetHandler */
	private final ResultSetHandler handler;
	
	/** the RecordHandlerFactory */
	private final RecordHandlerFactory recordHandlerFactory;
	
	/** the ExceptionHandler */
	private final ExceptionHandler exceptionHandler;
	
	/**
	 * @param name the name
	 * @param sql the SQL
	 * @param em the EntityManager
	 * @param queryId the queryId
	 * @param resultType the result type
	 * @param useRowSql the useRowSql
	 * @param builder the builder
	 * @param handler the handler 
	 * @param recordHandlerFactory the factory
	 * @param exceptionHandler the exceptionHandler
	 */
	
	public InternalEclipseLinkNativeQueryImpl(String name, 
			String sql,
			EntityManager em, 
			String queryId, 
			Class<?> resultType,
			boolean useRowSql, 
			SQLBuilder builder,
			ResultSetHandler handler,
			RecordHandlerFactory recordHandlerFactory,
			ExceptionHandler exceptionHandler
			) {
		
		super(name, sql, em, queryId, resultType, useRowSql, builder);
		this.handler = handler;
		this.recordHandlerFactory = recordHandlerFactory;
		this.exceptionHandler = exceptionHandler;
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.AbstractInternalQuery#getResultList()
	 */
	@Override
	public <T> List<T> getResultList() {
		Query query = mapping(createQuery());	
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		ResultSet rs = cursor.getResultSet();
		
		try {
			return handler.getResultList(rs, resultType, new DelegatingResultSetFilter(filter));
		}catch (Throwable e) {
			throw exceptionHandler.rethrow(e);
		}finally{
			cursor.close();
		}
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.AbstractInternalQuery#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult() {
		setMaxResults(1);
		List<T> result = getResultList();
		return result.isEmpty() ? null : result.get(0);
	}
	
	/**
	 * @see kosmos.framework.jpqlclient.internal.free.AbstractInternalJpaNativeQuery#getTotalResult()
	 */
	@Override
	public NativeResult getTotalResult(){

		Query query = mapping(createQuery());
		query.setMaxResults(0);
		
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		ResultSet rs = cursor.getResultSet();
		QueryResult result;
		try {
			result = handler.getResultList(rs, resultType, new DelegatingResultSetFilter(filter),getMaxResults(),getFirstResult());
		}catch (Throwable e) {
			throw exceptionHandler.rethrow(e);
		}finally{
			cursor.close();
		}
		return new NativeResult(result.isLimited(),result.getResultList(),result.getHitCount());
		
	}
	
	/**
	 * @see kosmos.framework.jpqlclient.internal.free.AbstractInternalJpaNativeQuery#getFetchResult()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getFetchResult(){
		Query query = mapping(createQuery());		
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		try{
			return new LazyList(cursor, recordHandlerFactory.create(resultType, cursor.getResultSet()),exceptionHandler);
		} catch (Throwable e) {
			cursor.close();
			throw exceptionHandler.rethrow(e);
		}	
	}
	
	/**
	 * Creates the query.
	 * 
	 * @return the query
	 */
	protected Query createQuery() {
		List<Object> bindList = new ArrayList<Object>();
		firingSql = buildSql(bindList);		
		Query query = name != null ? createNamedQuery() : em.createNativeQuery(firingSql);
		return bindParmaeterToQuery(query, bindList);			
	}
	
	/**
	 * Mapping the hint.
	 * 
	 * @param query the query
	 * @return the query
	 */
	protected Query mapping(Query query){
				
		for(Map.Entry<String, Object> h : getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}		
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

}
