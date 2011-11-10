/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.free;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import framework.sqlclient.api.free.NativeResult;
import framework.sqlclient.api.free.ResultSetFilter;
import framework.sqlengine.builder.SQLBuilder;

/**
 * The internal native query for JPA.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractInternalJPANativeQuery<T> extends AbstractInternalJPAQuery {

	/** the type of the result */
	protected final Class<T> resultType;
	
	/** 
	 * the name of the query. 
	 * only <code>javax.persistence.NamedNativeQuery</code> is required to use name. 
	 */
	protected final String name;
	
	/** the <code>SQLBuilder</code> */
	protected final SQLBuilder builder;
	
	/** the filter for <code>ResultSet</code> */
	protected ResultSetFilter<T> filter;
	
	/**
	 * @param name the name
	 * @param sql the SQL
	 * @param em the EntityManager
	 * @param queryId the queryId
	 * @param resultType the result type
	 * @param useRowSql the useRowSql
	 * @param builder the builder
	 */
	public AbstractInternalJPANativeQuery(String name ,String sql,EntityManager em, String queryId, Class<T> resultType,boolean useRowSql,SQLBuilder builder) {
		super(useRowSql,sql, em, queryId);		
		this.name = name;
		this.resultType = resultType;
		this.builder = builder;
	}
	
	/**
	 * @param filter the filter to set
	 * @return self
	 */
	public void setFilter(ResultSetFilter<T> filter){
		this.filter = filter;
	}
	
	/**
	 * Fetch the result.
	 * 
	 * @return the result
	 */
	@SuppressWarnings("rawtypes")
	public abstract List getFetchResult();

	/**
	 * Gets the total result.
	 * 
	 * @return the result
	 */
	public abstract NativeResult<T> getTotalResult();
	
	
	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#count()
	 */
	@Override
	public int count(){		

		List<Object> bindList = new ArrayList<Object>();
		firingSql = buildSql(bindList);
		
		//countの場合は範囲設定無効とする。
		
		firingSql = builder.setCount(firingSql);
		Query query = name != null ? createNamedQuery() : em.createNativeQuery(firingSql);
		query = bindParmaeterToQuery(query, bindList);	
		
		for(Map.Entry<String, Object> h : hints.entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}
		Object value = query.getSingleResult();
		return Integer.parseInt(value.toString());
	}
	
	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate() {
		List<Object> bindList = new ArrayList<Object>();
		Query query = null;
		if(name != null){
			query = em.createNamedQuery(firingSql);			
		}else {
			firingSql = buildSql(bindList);
			query = em.createNativeQuery(firingSql);
		}
		query = bindParmaeterToQuery(query, bindList);			
		
		for(Map.Entry<String, Object> h : hints.entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}	

		return query.executeUpdate();
	}
	

	/**
	 * Creates the named query.
	 * 
	 * @return the query
	 */
	protected Query createNamedQuery(){
		Query query = em.createNamedQuery(firingSql);
		for(Map.Entry<String, Object> h : hints.entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}		
		if(firstResult > 0){
			query.setFirstResult(firstResult);
		}
		if(maxSize > 0){
			query.setMaxResults(maxSize);
		}
		return query;
	}
	
	/**
	 * Builds the SQL.
	 * 
	 * @param bindList the bind parameters
	 * @return the SQL
	 */
	protected String buildSql(List<Object> bindList){
		String str = sql;	
		if(!useRowSql){
			str = builder.build(queryId, str);
			str = builder.evaluate(str, branchParam,queryId);
		}			
		str = builder.replaceToPreparedSql(str, param,bindList, queryId);			
		return str;
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

}
