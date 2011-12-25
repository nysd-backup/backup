/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.free.impl;

import kosmos.framework.sqlclient.api.Update;
import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.api.free.NativeUpdate;
import kosmos.framework.sqlclient.internal.free.AbstractLocalUpdateEngine;
import kosmos.framework.sqlclient.internal.free.InternalQuery;

/**
 * The updating engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class LocalUpdateEngineImpl extends AbstractLocalUpdateEngine<InternalQuery> implements NativeUpdate{

	/**
	 * @param delegate the delegate
	 */
	public LocalUpdateEngineImpl(InternalQuery delegate) {
		super(delegate);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends FreeUpdate> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update() {
		return delegate.executeUpdate();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#setHint(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Update> T setHint(String key, Object value) {
		delegate.setHint(key, value);
		return (T)this;
	}

}
