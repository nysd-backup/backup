/**
 * Copyright 2011 the original author
 */
package service.framework.core.async;

import java.lang.reflect.Proxy;


/**
 * The factory to create asynchronous services.
 * Creates a service as a dynamic proxy service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class AsyncServiceFactoryImpl implements AsyncServiceFactory{

	/**
	 * @see service.framework.core.async.AsyncServiceFactory#create(java.lang.Class)
	 */
	@Override
	public <T> T create(Class<T> serviceType){
		return serviceType.cast(Proxy.newProxyInstance(serviceType.getClassLoader(), new Class[]{serviceType}, 
				new AsyncServiceProxy()));
	}
	
}
