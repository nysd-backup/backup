/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.free;

import java.util.ArrayList;
import java.util.List;

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
public abstract class AbstractInternalJPANativeQueryImpl<T> extends AbstractInternalJPAQuery {

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
	public AbstractInternalJPANativeQueryImpl(String name ,String sql,EntityManager em, String queryId, Class<T> resultType,boolean useRowSql,SQLBuilder builder) {
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
	 * @see framework.jpqlclient.internal.free.AbstractInternalJPAQuery#createQuery()
	 */
	@Override
	protected Query createQuery() {
		
		Query query = null;
		List<Object> bindList = new ArrayList<Object>();
		//名前付き
		if( name != null){
			query = creatNamedNativeQuery(bindList);
		//名前なし	
		}else {			
			String str = sql;	
			if(!useRowSql){
				str = builder.build(queryId, str);
				str = builder.evaluate(str, branchParam,queryId);
			}
			str = builder.replaceToPreparedSql(str, param,bindList, queryId);
			firingSql = str;			
			query = creatNativeQuery(bindList);
		}
		for(int i=0; i < bindList.size(); i++){			
			query.setParameter(i+1,bindList.get(i));			
		}		
		return query;		
		
	}
	
	/**
	 * @return the native query;
	 */
	protected Query creatNativeQuery(List<Object> bindList){
		return em.createNativeQuery(firingSql);		
	}
	
	/**
	 * @return the native query;
	 */
	protected Query creatNamedNativeQuery(List<Object> bindList){
		if( resultType != null){
			return em.createNamedQuery(firingSql, resultType);
		}else{
			return em.createNamedQuery(firingSql);
		}		
	}

}
