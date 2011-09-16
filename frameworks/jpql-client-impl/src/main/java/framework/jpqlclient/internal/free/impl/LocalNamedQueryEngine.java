/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.internal.free.impl;

import java.util.List;

import javax.persistence.LockModeType;

import framework.jpqlclient.api.free.NamedQuery;
import framework.sqlclient.api.EmptyHandler;
import framework.sqlclient.api.free.FreeQuery;
import framework.sqlclient.internal.AbstractLocalQueryEngine;

/**
 *　NamedQueryの実行エンジン.
 *
 * @author	yoshida-n
 * @version	created.
 */
@SuppressWarnings("unchecked")
public class LocalNamedQueryEngine extends AbstractLocalQueryEngine<InternalNamedQueryImpl> implements NamedQuery{
	
	/** 0件時エラーハンドラ */
	private final EmptyHandler emptyHandler;
	
	/**
	 * @param delegate クエリ
	 * @param em エンティティマネージャ
	 * @param lazyDetach true:detachを即時実行しない
	 * @param handler ハンドラー
	 */
	public LocalNamedQueryEngine(InternalNamedQueryImpl delegate , EmptyHandler handler){
		super(delegate);	
		this.emptyHandler = handler;
	}
	
	/**
	 * @see framework.sqlclient.internal.AbstractLocalQueryEngine#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1){
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see framework.jpqlclient.api.free.NamedQuery#setLockMode(javax.persistence.LockModeType)
	 */
	@Override
	public <T extends NamedQuery> T setLockMode(LockModeType arg0) {
		 delegate.setLockMode(arg0);
		 return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public <T> List<T> getResultList() {
		List<T> result = delegate.getResultList();
		if(result.isEmpty()){
			if(nodataError){
				emptyHandler.handleEmptyResult(delegate);
			}
		}
		return result;
	}

	/**
	 * @see framework.sqlclient.api.free.FreeQuery#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setHint(String arg0, Object arg1) {
		delegate.setHint(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		return delegate.count();
	}

}
