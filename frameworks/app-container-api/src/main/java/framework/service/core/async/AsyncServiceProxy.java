/**
 * Use is subject to license terms.
 */
package framework.service.core.async;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Future;
import framework.service.core.locator.ServiceLocator;

/**
 * 非同期サービス用プロキシ.
 *
 * @author	yoshida-n
 * @version	2011/01/16 new create
 */
public class AsyncServiceProxy implements InvocationHandler{

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		AsyncService executor = ServiceLocator.lookupByInterface(AsyncService.class);

		Future<Object> futureResult = executor.execute(proxy, method, args);
		
		//未来結果オブジェクトをProxyにして返却
		Class<?>[] retType = new Class[0];
		if(method.getReturnType() != void.class){
			retType = new Class[]{method.getReturnType()};
		}
		return Proxy.newProxyInstance(method.getReturnType().getClassLoader(),retType, 
			new AsyncResultProxy(futureResult));
	}

}
