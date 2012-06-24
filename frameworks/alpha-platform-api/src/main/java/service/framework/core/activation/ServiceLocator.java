/**
 * Copyright 2011 the original author
 */
package service.framework.core.activation;

import java.lang.reflect.InvocationHandler;

import service.client.messaging.MessageClientFactory;
import service.framework.core.async.AsyncService;
import service.framework.core.async.AsyncServiceFactory;
import service.framework.core.transaction.ServiceContext;
import core.activation.ComponentLocator;



/**
 * A service locator.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceLocator extends ComponentLocator{

	/**
	 * @return the <code>MessageClientFactory</code>
	 */
	public abstract MessageClientFactory createMessageClientFactory();

	/**
	 * @return the JMS publisher
	 */
	public abstract InvocationHandler createPublisher();
	
	/**
	 * @return the JMS sender
	 */
	public abstract InvocationHandler createSender();
	
	/**
	 * @return the <code>AsyncServiceFactory</code>
	 */
	public abstract AsyncServiceFactory createAsyncServiceFactory();
	
	/**
	 * @return the <code>AsyncService</code>
	 */
	public abstract AsyncService createAsyncService();
	
	/**
	 * @return the ServiceContext
	 */
	public abstract ServiceContext createServiceContext();
	
	/**
	 * @param serviceName the serviceName to lookup 
	 * @return the service
	 */
	public abstract Object lookup(String serviceName);
	
	/**
	 * @param serviceName the serviceName to lookup 
	 * @return the service
	 */
	public abstract Object lookup(Class<?> ifType);
	
	/**
	 * @param serviceName the serviceName to lookup
	 * @return the service
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getService(String serviceName){
		return (T)getDelegate().lookup(serviceName);
	}
	
	/**
	 * @param serviceType the serviceType to lookup
	 * @return the service
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getService(Class<T> serviceType){
		return (T)getDelegate().lookup(serviceType);
	}
	
	/**
	 * @return the MessageClientFactory
	 */
	public static MessageClientFactory createDefaultMessageClientFactory(){
		return getDelegate().createMessageClientFactory();
	}
	
	/**
	 * @return the InvocationHandler
	 */
	public static InvocationHandler createDefaultPublisher(){
		return getDelegate().createPublisher();
	}
	
	/**
	 * @return the InvocationHandler
	 */
	public static InvocationHandler createDefaultSender(){
		return getDelegate().createSender();
	}

	/**
	 * @return the AsyncServiceFactory
	 */
	public static AsyncServiceFactory createDefaultAsyncServiceFactory(){
		return getDelegate().createAsyncServiceFactory();
	}
	
	/**
	 * @return the AsyncService
	 */
	public static AsyncService createDefaultAsyncService(){
		return getDelegate().createAsyncService();
	}
	
	/**
	 * @return the ServiceContext
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ServiceContext> T createDefaultServiceContext(){
		return (T)getDelegate().createServiceContext();
	}		
	
	/**
	 * @return
	 */
	private static ServiceLocator getDelegate(){
		return (ServiceLocator)delegate;
	}
	
}
