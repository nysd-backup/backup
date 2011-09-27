/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.free.impl;

import framework.sqlclient.api.free.FreeUpdate;
import framework.sqlclient.api.free.NativeUpdate;
import framework.sqlclient.internal.AbstractLocalUpdateEngine;

/**
 * NativeQueryの更新エンジン.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class LocalJPANativeUpdateEngine extends AbstractLocalUpdateEngine<InternalNativeJPAQueryImpl> implements NativeUpdate{

	/**
	 * @param delegate クエリ
	 */
	public LocalJPANativeUpdateEngine(InternalNativeJPAQueryImpl delegate) {
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
	 * @see framework.sqlclient.api.free.FreeUpdate#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends FreeUpdate> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

}
