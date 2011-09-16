/**
 * Use is subject to license terms.
 */
package framework.web.core.api.service;

import java.lang.reflect.InvocationHandler;

/**
 * サービスデリゲート.
 *
 * @author yoshida-n
 * @version	2011/06/14 created.
 */
public interface BusinessDelegate extends InvocationHandler{

	/**
	 * @param alias Beanのエイリアス
	 */
	public void setAlias(String alias);
	
}
