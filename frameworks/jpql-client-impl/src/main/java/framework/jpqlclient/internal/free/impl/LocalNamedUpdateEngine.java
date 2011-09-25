/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.internal.free.impl;

import framework.jpqlclient.api.free.NamedUpdate;
import framework.sqlclient.api.free.FreeUpdate;
import framework.sqlclient.internal.AbstractLocalUpdateEngine;

/**
 * NamedQueryの更新用エンジン.
 *
 * @author yoshida-n
 * @version	created.
 */
@SuppressWarnings("unchecked")
public class LocalNamedUpdateEngine extends AbstractLocalUpdateEngine<InternalNamedQueryImpl> implements NamedUpdate{

	/**
	 * @param delegate クエリ
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
