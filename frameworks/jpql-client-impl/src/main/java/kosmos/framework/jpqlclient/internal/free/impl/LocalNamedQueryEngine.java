/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.free.impl;

import java.util.List;

import javax.persistence.LockModeType;

import kosmos.framework.jpqlclient.api.free.NamedQuery;
import kosmos.framework.sqlclient.api.Query;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.FreeQueryParameter;
import kosmos.framework.sqlclient.internal.free.AbstractLocalQueryEngine;
import kosmos.framework.sqlclient.internal.free.InternalQuery;


/**
 *　The named query engine.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class LocalNamedQueryEngine extends AbstractLocalQueryEngine implements NamedQuery{
	
	/**
	 * @param delegate the delegate to set
	 * @param handler the handler to set
	 */
	public LocalNamedQueryEngine(InternalQuery internalQuery, FreeQueryParameter param){
		super(internalQuery,param);	
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.AbstractLocalQueryEngine#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1){
		condition.getBranchParam().put(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.jpqlclient.api.free.NamedQuery#setLockMode(javax.persistence.LockModeType)
	 */
	@Override
	public <T extends NamedQuery> T setLockMode(LockModeType arg0) {
		condition.setLockMode(arg0);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public <T> List<T> getResultList() {
		List<T> result = internalQuery.getResultList(condition);
		return result;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends Query> T setHint(String arg0, Object arg1) {
		condition.getHints().put(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		return internalQuery.count(condition);
	}

	

}
