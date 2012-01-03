/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.free.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import kosmos.framework.jpqlclient.internal.free.AbstractInternalNativeQuery;
import kosmos.framework.jpqlclient.internal.free.LazyList;
import kosmos.framework.sqlclient.api.free.FreeQueryParameter;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.internal.free.impl.DelegatingResultSetFilter;
import kosmos.framework.sqlengine.exception.ExceptionHandler;
import kosmos.framework.sqlengine.exception.impl.ExceptionHandlerImpl;
import kosmos.framework.sqlengine.executer.RecordHandlerFactory;
import kosmos.framework.sqlengine.executer.ResultSetHandler;
import kosmos.framework.sqlengine.executer.impl.RecordHandlerFactoryImpl;
import kosmos.framework.sqlengine.executer.impl.ResultSetHandlerImpl;
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
public class InternalEclipseLinkNativeQueryImpl extends AbstractInternalNativeQuery{

	/** the ResultSetHandler */
	private ResultSetHandler handler = new ResultSetHandlerImpl();
	
	/** the RecordHandlerFactory */
	private RecordHandlerFactory recordHandlerFactory = new RecordHandlerFactoryImpl();
	
	/** the ExceptionHandler */
	private ExceptionHandler exceptionHandler = new ExceptionHandlerImpl();
	
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
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getResultList(kosmos.framework.sqlclient.api.free.FreeQueryParameter)
	 */
	@Override
	public <T> List<T> getResultList(FreeQueryParameter parameter) {
		Query query = mapping(parameter,createQuery(parameter));	
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		ResultSet rs = cursor.getResultSet();
		
		try {
			return handler.getResultList(rs, parameter.getResultType(), new DelegatingResultSetFilter(parameter.getFilter()));
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
	public <T> T getSingleResult(FreeQueryParameter parameter) {
		parameter.setMaxSize(1);
		List<T> result = getResultList(parameter);
		return result.isEmpty() ? null : result.get(0);
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getTotalResult(kosmos.framework.sqlclient.api.free.FreeQueryParameter)
	 */
	@Override
	public NativeResult getTotalResult(FreeQueryParameter parameter) {

		Query query = mapping(parameter,createQuery(parameter));
		query.setMaxResults(0);
		
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		ResultSet rs = cursor.getResultSet();
		QueryResult result;
		try {
			result = handler.getResultList(rs, parameter.getResultType(), new DelegatingResultSetFilter(parameter.getFilter()),
					parameter.getMaxSize(),parameter.getFirstResult());
		}catch (Throwable e) {
			throw exceptionHandler.rethrow(e);
		}finally{
			cursor.close();
		}
		return new NativeResult(result.isLimited(),result.getResultList(),result.getHitCount());
		
	}
	
	/**
	 * @see kosmos.framework.jpqlclient.internal.free.AbstractInternalNativeQuery#getFetchResult()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getFetchResult(FreeQueryParameter parameter) {
		Query query = mapping(parameter,createQuery(parameter));		
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		try{
			return new LazyList(cursor, recordHandlerFactory.create(parameter.getResultType(), cursor.getResultSet()),exceptionHandler);
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
	protected Query createQuery(FreeQueryParameter param) {
		List<Object> bindList = new ArrayList<Object>();
		String executingSql = buildSql(bindList,param);		
		Query query = param.getName() != null ? createNamedQuery(executingSql, param) : em.createNativeQuery(executingSql);
		return bindParmaeterToQuery(query, bindList);			
	}
	
	/**
	 * Mapping the hint.
	 * 
	 * @param query the query
	 * @return the query
	 */
	protected Query mapping(FreeQueryParameter parameter,Query query){
				
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
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#batchUpdate(kosmos.framework.sqlclient.api.free.FreeUpdateParameter)
	 */
	@Override
	public int[] batchUpdate(FreeUpdateParameter parameter) {
		throw new UnsupportedOperationException();
	}

}
