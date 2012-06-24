/**
 * Copyright 2011 the original author
 */
package service.framework.core.async;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;

import service.framework.core.activation.ServiceLocator;


/**
 * The factory to create asynchronous services.
 * Creates a service as a dynamic proxy service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractAsyncServiceFactory implements AsyncServiceFactory{

	/**
	 * @see service.framework.core.async.AsyncServiceFactory#create(java.lang.Class)
	 */
	@Override
	public <T> T create(Class<T> serviceType){
		T service = ServiceLocator.getService(serviceType);
		//Future<V>を直接返却するAsyncronousサービス以外はプロキシを使用する。
		if( service != null && serviceType.getAnnotation(getAnnotation()) == null){
			return serviceType.cast(Proxy.newProxyInstance(serviceType.getClassLoader(), new Class[]{serviceType}, 
				new AsyncServiceProxy()));
		}
		return service;
	}
	
	/**
	 * @return the annotation that indicates 'AsyncService'
	 */
	protected abstract Class<? extends Annotation> getAnnotation();
}
