/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api.free;

import javax.persistence.LockModeType;

import framework.sqlclient.api.free.FreeQuery;


/**
 * NamedQueryのAPI.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface NamedQuery extends FreeQuery{
	
	/**
	 * JPAHint.
	 * @param <T> 型
	 * @param arg0 キー
	 * @param arg1 値
	 * @return self
	 */
	public <T extends FreeQuery> T setHint(String arg0 , Object arg1);
	
	/**
	 * @param <T> 型
	 * @param arg0 ロックモード
	 * @return self
	 */
	public <T extends NamedQuery> T setLockMode(LockModeType arg0);

	
}
