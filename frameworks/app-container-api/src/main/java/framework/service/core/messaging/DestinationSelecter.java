/**
 * Use is subject to license terms.
 */
package framework.service.core.messaging;

import java.lang.reflect.Method;

/**
 * Queue/Topic宛先を取得する.
 *
 * @author yoshida-n
 * @version	2011/05/07 created.
 */
public interface DestinationSelecter {

	/**
	 * @param target 実行メソッド
	 * @return　宛先
	 */
	public String createDestinationName(Method target);
}
