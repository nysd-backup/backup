/**
 * Copyright 2011 the original author
 */
package framework.service.core.async;

import java.lang.reflect.Method;
import java.util.concurrent.Future;

/**
 * 非同期実行エンジン.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
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
