/**
 * Copyright 2011 the original author
 */
package framework.service.core.query;

import javax.persistence.LockModeType;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import framework.jpqlclient.api.free.AbstractNamedQuery;
import framework.jpqlclient.api.free.NamedQuery;

/**
 * EclipseLinkのオプションを使用したNamedQuery.

 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class EclipseLinkNamedQuery extends AbstractNamedQuery{

	/**
	 * SQLヒント句を設定する。
	 * @param <Q> 型
	 * @param hintValue ヒント句
	 * @return self
	 */
	public <Q extends NamedQuery> Q setHintString(String hintValue){
		setHint(QueryHints.HINT, hintValue);
		return (Q)this;
	}
	
	/**
	 * DBから直接検索し永続化コンテキストを最新の値で更新する。
	 * @param <Q> 型
	 * @return self
	 */
	public <Q extends NamedQuery> Q setRefleshMode(){
		setHint(QueryHints.REFRESH, HintValues.TRUE);
		return (Q)this;
	}
	
	/**
	 * 悲観ロックを設定する。
	 * @param <Q> 型
	 * @param timeout タイムアウト
	 * @return self
	 */
	public <Q extends NamedQuery> Q setPessimisticRead(){
		setLockMode(LockModeType.PESSIMISTIC_READ);
		return (Q)this;
	}
}
