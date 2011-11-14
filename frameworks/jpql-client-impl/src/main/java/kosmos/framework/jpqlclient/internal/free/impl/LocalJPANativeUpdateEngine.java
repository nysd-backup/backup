/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.free.impl;

import kosmos.framework.jpqlclient.internal.free.AbstractInternalJPANativeQuery;
import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.api.free.NativeUpdate;
import kosmos.framework.sqlclient.internal.AbstractLocalUpdateEngine;

/**
 * The native update engine for JPA.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class LocalJPANativeUpdateEngine extends AbstractLocalUpdateEngine<AbstractInternalJPANativeQuery<?>> implements NativeUpdate{

	/**
	 * @param delegate the delegate to set
	 */
	public LocalJPANativeUpdateEngine(AbstractInternalJPANativeQuery<?> delegate) {
		super(delegate);		
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update() {
		return delegate.executeUpdate();
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
	 * @see kosmos.framework.sqlclient.api.free.NativeUpdate#setQueryTimeout(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends NativeUpdate> T setQueryTimeout(int seconds) {
		delegate.setQueryTimeout(seconds);
		return (T)this;
	}

}
