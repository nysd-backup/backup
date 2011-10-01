/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.internal.impl;

import framework.sqlclient.api.free.FreeUpdate;
import framework.sqlclient.api.free.NativeUpdate;
import framework.sqlclient.internal.AbstractLocalUpdateEngine;

/**
 * The updating engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class UpdateEngineImpl extends AbstractLocalUpdateEngine<InternalQueryImpl<?>> implements NativeUpdate{

	/**
	 * @param delegate the delegate
	 */
	public UpdateEngineImpl(InternalQueryImpl<?> delegate) {
		super(delegate);
	}

	/**
	 * @see framework.sqlclient.api.free.FreeUpdate#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends FreeUpdate> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update() {
		return delegate.executeUpdate();
	}

}
