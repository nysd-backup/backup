/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.free;

import java.util.List;

import javax.persistence.LockModeType;

import kosmos.framework.sqlclient.api.free.AbstractQuery;


/**
 *　The named query engine.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class NamedQueryImpl extends AbstractQuery implements NamedQuery{
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.AbstractQuery#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public NamedQuery setBranchParameter(String arg0, Object arg1){
		condition.getBranchParam().put(arg0, arg1);
		return this;
	}

	/**
	 * @see kosmos.framework.jpqlclient.api.free.NamedQuery#setLockMode(javax.persistence.LockModeType)
	 */
	@Override
	public NamedQuery setLockMode(LockModeType arg0) {
		condition.setLockMode(arg0);
		return this;
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
	public NamedQuery setHint(String arg0, Object arg1) {
		condition.getHints().put(arg0, arg1);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#count()
	 */
	@Override
	public long count() {
		return internalQuery.count(condition);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setSql(java.lang.String)
	 */
	@Override
	public NamedQuery setSql(String sql) {
		condition.setSql(sql);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setSqlId(java.lang.String)
	 */
	@Override
	public NamedQuery setSqlId(String sqlId) {
		condition.setQueryId(sqlId);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setResultType(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public NamedQuery setResultType(Class resultType) {
		condition.setResultType(resultType);
		return this;
	}

}
