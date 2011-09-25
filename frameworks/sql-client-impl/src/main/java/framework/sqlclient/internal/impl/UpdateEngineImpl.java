/**
 * Use is subject to license terms.
 */
package framework.sqlclient.internal.impl;

import framework.sqlclient.api.free.FreeUpdate;
import framework.sqlclient.api.free.NativeUpdate;
import framework.sqlclient.internal.AbstractLocalUpdateEngine;

/**
 * 内部更新実行エンジン.
 *
 * @author yoshida-n
 * @version	created.
 */
public class UpdateEngineImpl extends AbstractLocalUpdateEngine<InternalQueryImpl<?>> implements NativeUpdate{

	/**
	 * @param delegate delegate
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
