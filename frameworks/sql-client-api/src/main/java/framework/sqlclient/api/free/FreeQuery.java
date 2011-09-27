/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;

import framework.sqlclient.api.Query;


/**
 * SQL/JPQL/CQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface FreeQuery extends Query{
	
	/**
	 * if文用パラメータ
	 * @param <T> 型
	 * @param arg0 キー
	 * @param arg1 値
	 * @return self
	 */
	public <T extends FreeQuery> T setBranchParameter(String arg0 , Object arg1);

	/**
	 * バインドパラメータ
	 * @param <T> 型
	 * @param arg0 キー
	 * @param arg1 値
	 * @return self
	 */
	public <T extends FreeQuery> T setParameter(String arg0 , Object arg1);
	
}
