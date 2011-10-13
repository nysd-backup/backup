/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.free.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.ScrollableCursor;

import framework.jpqlclient.internal.free.AbstractInternalJPANativeQueryImpl;
import framework.sqlclient.api.free.NativeResult;
import framework.sqlclient.internal.impl.DelegatingResultSetFilter;
import framework.sqlengine.builder.SQLBuilder;
import framework.sqlengine.executer.RecordHandlerFactory;
import framework.sqlengine.executer.ResultSetHandler;
import framework.sqlengine.facade.QueryResult;

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
public class InternalEclipseLinkNativeQueryImpl<T> extends AbstractInternalJPANativeQueryImpl<T>{

	/** the ResultSetHandler */
	private final ResultSetHandler handler;
	
	/** the RecordHandlerFactory */
	private final RecordHandlerFactory recordHandlerFactory;
	
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
	 */
	
	public InternalEclipseLinkNativeQueryImpl(String name, String sql,
			EntityManager em, String queryId, Class<T> resultType,
			boolean useRowSql, SQLBuilder builder,ResultSetHandler handler,RecordHandlerFactory recordHandlerFactory) {
		
		super(name, sql, em, queryId, resultType, useRowSql, builder);
		this.handler = handler;
		this.recordHandlerFactory = recordHandlerFactory;
	}
	
	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#getResultList()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getResultList() {
		Query query = mapping(createQuery());	
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		ResultSet rs = cursor.getResultSet();
		QueryResult<T> result;
		try {
			result = handler.getResultList(rs, resultType, -1, false, queryId, 
					new DelegatingResultSetFilter<T>(filter));
		} catch (SQLException e) {
			cursor.close();
			throw new IllegalStateException(e);
		}
		return result.getResultList();
	}

	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#getSingleResult()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getSingleResult() {
		setMaxResults(1);
		List<T> result = getResultList();
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}
	
	/**
	 * @see framework.jpqlclient.internal.free.AbstractInternalJPANativeQueryImpl#getTotalResult()
	 */
	@Override
	public NativeResult<T> getTotalResult(){
		int saved = getMaxResults();
		setMaxResults(-1);
		Query query = mapping(createQuery());
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		ResultSet rs = cursor.getResultSet();
		QueryResult<T> result;
		try {
			result = handler.getResultList(rs, resultType, saved, true, queryId,
					new DelegatingResultSetFilter<T>(filter));
			return new NativeResult<T>(result.isLimited(),result.getResultList(),result.getHitCount());
		} catch (SQLException e) {
			cursor.close();
			throw new IllegalStateException(e);
		}		
		
	}
	
	/**
	 * @see framework.jpqlclient.internal.free.AbstractInternalJPANativeQueryImpl#getFetchResult()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getFetchResult(){
		Query query = mapping(createQuery());		
		ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
		try{
			return new LazyList<T>(cursor, recordHandlerFactory.create(resultType, cursor.getResultSet()));
		} catch (SQLException e) {
			cursor.close();
			throw new IllegalStateException(e);
		}	
	}
	
	/**
	 * @see framework.jpqlclient.internal.free.AbstractInternalJPAQuery#mapping(javax.persistence.Query)
	 */
	@Override
	protected Query mapping(Query query){
				
		for(Map.Entry<String, Object> h : hints.entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}		
		//自分で範囲設定するためfirstResultとmaxResultのqueryへの設定を実施しない。
		
		//ResultSetの取得を可能とする。
		query.setHint(QueryHints.SCROLLABLE_CURSOR, HintValues.TRUE);
		return query;
	}
	
	/**
	 * @see framework.jpqlclient.internal.free.AbstractInternalJPANativeQueryImpl#creatNativeQuery()
	 */
	@Override
	protected Query creatNativeQuery(List<Object> bindList){
		//NativeQueryは自動でSQLで範囲設定してくれないので、自分で範囲設定する。
		firingSql = builder.setRange(firingSql, getFirstResult(), getMaxResults(), bindList);
		return em.createNativeQuery(firingSql);		
	}
	
}
