/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api.free;

import framework.sqlclient.api.Update;


/**
 * アップデート.
 *
 * @author yoshida-n
 * @version	created.
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
