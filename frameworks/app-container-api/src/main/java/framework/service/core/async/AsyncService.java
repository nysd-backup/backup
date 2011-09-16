/**
 * Use is subject to license terms.
 */
package framework.service.core.async;

import java.lang.reflect.Method;
import java.util.concurrent.Future;

/**
 * 非同期実行エンジン.
 *
 * @author	yoshida-n
 * @version	2011/01/16 new create
 */
public interface AsyncService {

	/**
	 * @param proxy プロキシ
	 * @param method メソッド
	 * @param args 引数
	 * @return 未来結果
	 */
	public Future<Object> execute(Object proxy, Method method , Object args) throws Exception;
}
