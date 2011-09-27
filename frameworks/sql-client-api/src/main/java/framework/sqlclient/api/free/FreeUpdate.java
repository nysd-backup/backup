/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;

import framework.sqlclient.api.Update;


/**
 * アップデート.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface FreeUpdate extends Update{
	
	/**
	 * バインドパラメータ
	 * @param <T>　型
	 * @param arg0 パラメータ名
	 * @param arg1 パラメータ
	 * @return self
	 */
	public <T extends FreeUpdate> T setParameter(String arg0 , Object arg1);
	

	/**
	 * if文用パラメータ
	 * @param <T>　型
	 * @param arg0 パラメータ名
	 * @param arg1 パラメータ
	 * @return self
	 */
	public <T extends FreeUpdate> T setBranchParameter(String arg0, Object arg1);

	
}
