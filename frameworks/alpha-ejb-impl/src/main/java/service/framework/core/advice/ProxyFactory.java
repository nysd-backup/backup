/**
 * Copyright 2011 the original author
 */
package service.framework.core.advice;

import java.lang.reflect.Proxy;

import service.framework.core.advice.InternalInterceptor;


/**
 * Binds the interceptor to target object without EJB container.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class ProxyFactory {

	/**
	 * Creates the proxy.
	 * 
	 * @param interfaceOfService interface
	 * @param target target object
	 * @param interceptorObject interceptor
	 * @return service
	 */
	public static <T> T create(Class<T> interfaceOfService,T target,InternalInterceptor interceptorObject , String joinPointMethodName){
		
		DelegatingInterceptor interceptor = new DelegatingInterceptor(target,interceptorObject,joinPointMethodName);
		
		return interfaceOfService.cast(
				Proxy.newProxyInstance(interfaceOfService.getClassLoader(), new Class[]{interfaceOfService}, 
				interceptor));
				
	}
}
