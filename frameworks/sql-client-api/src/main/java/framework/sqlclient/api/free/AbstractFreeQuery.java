/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api.free;

import java.util.List;

import framework.sqlclient.api.Query;

/**
 * 自由検索クエリ.
 * SQL/JPQL/CQL(予定）の基底
 *
 * @author yoshida-n
 * @version　 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractFreeQuery<D extends FreeQuery> implements FreeQuery{
	
	/** クエリ */
	protected D delegate;
	
	/**
	 * @param delegate クエリ
	 */
	protected <T extends FreeQuery> T setDelegate(D delegate){
		this.delegate = delegate;
		return (T)this;
	}
	
	/**
	 * @return クエリ
	 */
	protected D getDelegate(){
		return delegate;
	}
	
	/**
	 * @see framework.sqlclient.api.free.FreeQuery#setBranchParameter(java.lang.String, java.lang.Object)
	 */

	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#enableNoDataError()
	 */
	@Override
	public <T extends Query> T enableNoDataError() {
		delegate.enableNoDataError();
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public <T> List<T> getResultList() {
		return (List<T>)delegate.getResultList();
	}
	
	/**
	 * @see framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult() {
		return (T)delegate.getSingleResult();
	}
	
	/**
	 * @see framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		return delegate.count();
	}

	/**
	 * @see framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <T extends Query> T setFirstResult(int arg0) {
		delegate.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <T extends Query> T setMaxResults(int arg0) {
		delegate.setMaxResults(arg0);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.free.FreeQuery#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setParameter(String arg0, Object arg1) {
		delegate.setParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#exists()
	 */
	@Override
	public boolean exists() {
		return delegate.exists();
	}

}

