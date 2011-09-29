/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.free;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import framework.sqlclient.internal.AbstractInternalQuery;

/**
 * The internal query for JPA.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractInternalJPAQuery extends AbstractInternalQuery{
	
	/** the flush mode */
	protected FlushModeType flush = null;
	
	/** the lock mode */
	protected LockModeType lock = null;
	
	/** the EntityManager */
	protected EntityManager em = null;
	
	/** the JPA hint */
	private Map<String,Object> hints = null;
	
	/**
	 * @param sql the SQL
	 * @param em the em
	 * @param queryId the queryId
	 */
	public AbstractInternalJPAQuery(boolean useRowSql,String sql, EntityManager em , String queryId){
		super(useRowSql,sql,queryId);
		this.em = em;
		this.hints = new HashMap<String,Object>();		
	}

	
	/**
	 * @return the new query
	 */
	protected abstract Query createQuery();
	
	/**
	 * @param arg0 the hint of the key
	 * @param arg1 the hint value
	 * @return self
	 */
	public AbstractInternalJPAQuery setHint(String arg0, Object arg1) {
		hints.put(arg0, arg1);
		return this;
	}
	
	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#getResultList()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getResultList() {
		return mapping( createQuery()).getResultList();
	}

	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#count()
	 */
	@Override
	public int count(){
		//TODO 
		throw new UnsupportedOperationException();
	}

	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#getSingleResult()
	 */
	@Override
	public Object getSingleResult() {
		setMaxResults(1);
		return mapping(createQuery()).getSingleResult();
	}
	
	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate() {
		return mapping(createQuery()).executeUpdate();
	}

	/**
	 * @return the flush mode
	 */
	public FlushModeType getFlushMode() {
		return flush;
	}

	/**
	 * @return the lock mode
	 */
	public LockModeType getLockMode() {
		return lock;
	}
	
	/**
	 * Set the parameter to query.
	 * 
	 * @param query the query
	 * @return the query
	 */
	private Query mapping(Query query){
				
		for(Map.Entry<String, Object> h : hints.entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}
		
		if( flush != null){
			query.setFlushMode(flush);
		}
		if( lock != null){
			query.setLockMode(lock);
		}
		if( maxSize > 0){
			query.setMaxResults(maxSize);
		}
		if( firstResult > 0){
			query.setFirstResult(firstResult);
		}
		return query;
	}

	/**
	 * @param arg0 the arg0 to set
	 * @return self
	 */
	public AbstractInternalJPAQuery setFlushMode(FlushModeType arg0) {
		flush = arg0;
		return this;
	}

	/**
	 * @param arg0 the arg0 to set
	 * @return self
	 */
	public AbstractInternalJPAQuery setLockMode(LockModeType arg0) {
		lock = arg0;
		return this;
	}

}
