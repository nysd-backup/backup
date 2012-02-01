/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.free;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.sqlclient.api.free.FreeParameter;
import kosmos.framework.sqlclient.api.free.FreeQueryParameter;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.internal.free.InternalQuery;
import kosmos.framework.sqlengine.builder.SQLBuilder;


/**
 * The internal native query for JPA.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractInternalNativeQuery implements InternalQuery{
	
	/** the EntityManager */
	protected EntityManager em;
	
	/** the <code>SQLBuilder</code> */
	private SQLBuilder builder;
	
	/**
	 * @param em the em to set
	 */
	public void setEntityManagerProvider(EntityManagerProvider em){
		this.em = em.getEntityManager();
	}
	
	/**
	 * @param builder the builder to set
	 */
	public void setSqlBuilder(SQLBuilder builder){
		this.builder = builder;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#count()
	 */
	@Override
	public long count(FreeQueryParameter param){		

		List<Object> bindList = new ArrayList<Object>();
		String executingSql = buildSql(bindList,param);
		
		//countの場合は範囲設定無効とする。
		executingSql = builder.setCount(executingSql);
		Query query = param.getName() != null ? createNamedQuery(executingSql,param) : em.createNativeQuery(executingSql);
		query = bindParmaeterToQuery(query, bindList);	
		
		for(Map.Entry<String, Object> h : param.getHints().entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}
		Object value = query.getSingleResult();
		return Long.parseLong(value.toString());
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate(FreeUpdateParameter param) {
		List<Object> bindList = new ArrayList<Object>();
		Query query = null;
		if(param.getName() != null){
			query = em.createNamedQuery(param.getName());			
		}else {
			String executingSql = buildSql(bindList,param);
			query = em.createNativeQuery(executingSql);
		}
		query = bindParmaeterToQuery(query, bindList);			
		
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
	protected Query createNamedQuery(String executingQuery,FreeQueryParameter param){
		Query query = em.createNamedQuery(executingQuery);
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
	protected String buildSql(List<Object> bindList, FreeParameter param){
		String str = param.getSql();	
		if(!param.isUseRowSql()){
			str = builder.build(param.getQueryId(), str);
			str = builder.evaluate(str, param.getBranchParam(),param.getQueryId());
		}			
		str = builder.replaceToPreparedSql(str, Arrays.asList(param.getParam()),Arrays.asList(bindList), param.getQueryId());			
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
