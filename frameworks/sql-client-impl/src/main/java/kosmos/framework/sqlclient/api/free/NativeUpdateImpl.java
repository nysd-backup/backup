/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import kosmos.framework.sqlclient.api.Update;


/**
 *　The updating engine.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class NativeUpdateImpl extends AbstractUpdate implements NativeUpdate{

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
	public <T extends FreeUpdate> T setBranchParameter(String arg0, Object arg1) {
		parameter.getBranchParam().put(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends Update> T setHint(String arg0, Object arg1) {
		parameter.getHints().put(arg0,arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setSql(java.lang.String)
	 */
	@Override
	public NativeUpdate setSql(String sql) {
		parameter.setSql(sql);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setSqlId(java.lang.String)
	 */
	@Override
	public NativeUpdate setSqlId(String sqlId) {
		parameter.setQueryId(sqlId);
		return this;
	}

}
