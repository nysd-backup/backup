/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.free;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import framework.sqlclient.internal.AbstractInternalQuery;

/**
 * The internal query for JPA.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractInternalJPAQuery extends AbstractInternalQuery{
	
	/** the EntityManager */
	protected final EntityManager em;

	/**
	 * @param sql the SQL
	 * @param em the em
	 * @param queryId the queryId
	 */
	public AbstractInternalJPAQuery(boolean useRowSql,String sql, EntityManager em , String queryId){
		super(useRowSql,sql,queryId);
		this.em = em;		
	}
	
	/**
	 * @return the new query
	 */
	protected abstract Query createQuery();

	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#count()
	 */
	@Override
	public int count(){
		//TODO 
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate() {
		return mapping(createQuery()).executeUpdate();
	}
	
	/**
	 * Set the parameter to query.
	 * 
	 * @param query the query
	 * @return the query
	 */
	protected Query mapping(Query query){
				
		for(Map.Entry<String, Object> h : hints.entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}
		if( maxSize > 0){
			query.setMaxResults(maxSize);
		}
		if( firstResult > 0){
			query.setFirstResult(firstResult);
		}
		return query;
	}


}
