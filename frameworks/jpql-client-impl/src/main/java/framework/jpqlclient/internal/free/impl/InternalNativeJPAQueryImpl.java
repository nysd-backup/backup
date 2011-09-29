/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.free.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import framework.jpqlclient.internal.free.AbstractInternalJPAQuery;
import framework.sqlengine.builder.SQLBuilder;

/**
 * The internal native query for JPA.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalNativeJPAQueryImpl extends AbstractInternalJPAQuery {

	/** the type of the result */
	private final Class<?> resultType;
	
	/** 
	 * the name of the query. 
	 * only <code>javax.persistence.NamedNativeQuery</code> is required to use name. 
	 */
	private String name = null;
	
	/** if true dont analiyze the template */
	private boolean useRowSql;
	
	/** the <code>SQLBuilder</code> */
	private final SQLBuilder builder;
	
	/**
	 * @param sql the SQL
	 * @param em the EntityManager
	 * @param queryId the queryId
	 * @param resultType the result type
	 */
	public InternalNativeJPAQueryImpl(String name ,String sql,EntityManager em, String queryId, Class<?> resultType,boolean useRowSql,SQLBuilder builder) {
		super(useRowSql,sql, em, queryId);		
		this.name = name;
		this.resultType = resultType;
		this.builder = builder;
	}

	/**
	 * @see framework.jpqlclient.internal.free.AbstractInternalJPAQuery#createQuery()
	 */
	@Override
	protected Query createQuery() {
		
		Query query = null;
		List<Object> bindList = new ArrayList<Object>();
		//名前付き
		if( name != null){
			if( resultType != null){
				query = em.createNamedQuery(firingSql, resultType);
			}else{
				query = em.createNamedQuery(firingSql);
			}		
		//名前なし	
		}else {			
			String str = sql;	
			if(!useRowSql){
				str = builder.build(queryId, str);
				str = builder.evaluate(str, branchParam,queryId);
			}
			str = builder.replaceToPreparedSql(str, param,bindList, queryId);
			firingSql = str;			
			if( resultType != null && !resultType.equals(void.class)){
				query = em.createNativeQuery(firingSql, resultType);
			}else{
				query = em.createNativeQuery(firingSql);
			}		
		}
		for(int i=0; i < bindList.size(); i++){			
			query.setParameter(i+1,bindList.get(i));			
		}		
		return query;		
		
	}

}
