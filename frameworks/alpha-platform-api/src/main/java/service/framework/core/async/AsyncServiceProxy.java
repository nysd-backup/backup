/**
 * Copyright 2011 the original author
 */
package service.framework.core.async;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Future;

import service.framework.core.activation.ServiceLocator;


/**
 * An interface of asynchronous proxy service.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
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
