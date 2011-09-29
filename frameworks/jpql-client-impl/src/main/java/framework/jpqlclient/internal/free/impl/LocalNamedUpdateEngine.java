/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.free.impl;

import framework.jpqlclient.api.free.NamedUpdate;
import framework.sqlclient.api.free.FreeUpdate;
import framework.sqlclient.internal.AbstractLocalUpdateEngine;

/**
 * The named update engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class LocalNamedUpdateEngine extends AbstractLocalUpdateEngine<InternalNamedQueryImpl> implements NamedUpdate{

	/**
	 * @param delegate the delegate to set.
	 */
	public LocalNamedUpdateEngine(InternalNamedQueryImpl delegate) {
		super(delegate);		
	}

	/**
	 * @see framework.sqlclient.api.Update#update()
	 */
	@Override
	public int update() {
		return delegate.executeUpdate();
	}

	/**
	 * @see framework.jpqlclient.api.free.NamedUpdate#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends NamedUpdate> T setHint(String arg0, Object arg1) {
		delegate.setHint(arg0,arg1);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.free.FreeUpdate#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeUpdate> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}
}
