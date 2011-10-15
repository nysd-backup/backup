/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.free;

import javax.persistence.EntityManager;

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

}
