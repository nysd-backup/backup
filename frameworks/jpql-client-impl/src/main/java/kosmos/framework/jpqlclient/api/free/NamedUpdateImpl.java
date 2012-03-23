/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.free;

import kosmos.framework.sqlclient.api.free.AbstractUpdate;

/**
 * The named update engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class NamedUpdateImpl extends AbstractUpdate implements NamedUpdate{

	/**
	 * @see kosmos.framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update() {
		return internalQuery.executeUpdate(parameter);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public NamedUpdate setBranchParameter(String arg0, Object arg1) {
		parameter.getBranchParam().put(arg0, arg1);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public NamedUpdate setHint(String arg0, Object arg1) {
		parameter.getHints().put(arg0,arg1);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setSql(java.lang.String)
	 */
	@Override
	public NamedUpdate setSql(String sql) {
		parameter.setSql(sql);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setSqlId(java.lang.String)
	 */
	@Override
	public NamedUpdate setSqlId(String sqlId) {
		parameter.setQueryId(sqlId);
		return this;
	}


}
