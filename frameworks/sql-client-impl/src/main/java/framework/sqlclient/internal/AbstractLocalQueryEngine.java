/**
 * Use is subject to license terms.
 */
package framework.sqlclient.internal;

import framework.sqlclient.api.Query;
import framework.sqlclient.api.free.FreeQuery;

/**
 * ローカルクエリ実行エンジン.
 *
 * @author yoshida-n
 * @version	created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractLocalQueryEngine<D extends AbstractInternalQuery> implements FreeQuery{

	/** クエリ */
	protected final D delegate;

	/** 検索結果0件時システムエラー有無 */
	protected boolean nodataError = false;

	/**
	 * @param delegate クエリ
	 */
	public AbstractLocalQueryEngine(D delegate){
		this.delegate = delegate;		
	}

	/**
	 * @see framework.api.sql.Query#enableNoDataError()
	 */
	@Override
	public <T extends Query> T enableNoDataError(){
		nodataError = true;
		return (T)this;
	}
	
	/**
	 * @see framework.sqlclient.api.free.free.api.sql.FreeQuery#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult() {
		return (T)delegate.getSingleResult();
	}

	/**
	 * @see framework.sqlclient.api.free.free.api.sql.FreeQuery#setFirstResult(int)
	 */
	@Override
	public <T extends Query> T setFirstResult(int arg0) {
		delegate.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.free.free.api.sql.FreeQuery#setMaxResults(int)
	 */
	@Override
	public <T extends Query> T setMaxResults(int arg0) {
		delegate.setMaxResults(arg0);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.free.free.api.sql.FreeQuery#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setParameter(String arg0 , Object arg1){
		delegate.setParameter(arg0, arg1);
		return (T)this;
	}
	
	/**
	 * @see framework.sqlclient.api.free.free.FreeQuery#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see framework.api.sql.Query#exists()
	 */
	@Override
	public boolean exists() {
		setMaxResults(1);
		return ! getResultList().isEmpty();
	}

	
}
