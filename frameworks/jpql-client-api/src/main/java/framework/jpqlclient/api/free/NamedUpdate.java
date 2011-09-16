/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.api.free;

import framework.sqlclient.api.free.FreeUpdate;


/**
 * NamedUpdateのAPI.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface NamedUpdate extends FreeUpdate{
	
	/**
	 * JPAHint.
	 * @param <T> 型
	 * @param arg0 キー
	 * @param arg1 値
	 * @return self
	 */
	public <T extends NamedUpdate> T setHint(String arg0 , Object arg1);

}
