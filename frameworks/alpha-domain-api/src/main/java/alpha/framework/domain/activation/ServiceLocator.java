/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.activation;

import alpha.framework.core.exception.BusinessException;
import alpha.framework.core.message.FaultLogger;
import alpha.framework.core.message.MessageBuilder;
import alpha.framework.domain.messaging.client.MessageClientFactory;



/**
 * A alpha.domain locator.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceLocator{
	
	/** the ServiceLocator */
	protected static ServiceLocator  delegate;
	
	/**
	 * @return the MessageClientFactoryImpl
	 */
	public abstract MessageClientFactory createMessageClientFactory();
	
	/**
	 * @param serviceName the serviceName to lookup 
	 * @return the alpha.domain
	 */
	public abstract Object lookup(String serviceName);
	
	/**
	 * @param serviceName the serviceName to lookup 
	 * @return the alpha.domain
	 */
	public abstract Object lookup(Class<?> ifType);
	
	/**
	 * @return the message builder
	 */
	public abstract MessageBuilder createMessageBuilder();	
	
	/**
	 * @return the fault notifier
	 */
	public abstract FaultLogger createFaultNotifier();	
	
	/**
	 * @return the BusinessException
	 */
	public abstract BusinessException createBusinessException();
	
	/**
	 * @return the MessageBuilder
	 */
	public static MessageBuilder createDefaultMessageBuilder(){
		return delegate.createMessageBuilder();
	}
	
	/**
	 * @return the BusinessException
	 */
	public static BusinessException createDefaultBusinessException(){
		return delegate.createBusinessException();
	}
	
	/**
	 * @return the fault error
	 */
	public static FaultLogger createDefaultFaultNotifier(){
		return delegate.createFaultNotifier();
	}
	
	/**
	 * @param serviceName the serviceName to lookup
	 * @return the alpha.domain
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getService(String serviceName){
		return (T)getDelegate().lookup(serviceName);
	}
	
	/**
	 * @param serviceType the serviceType to lookup
	 * @return the alpha.domain
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getService(Class<T> serviceType){
		return (T)getDelegate().lookup(serviceType);
	}
	
	/**
	 * @return the MessageClientFactoryImpl
	 */
	public static MessageClientFactory createDefaultMessageClientFactory() {
		return getDelegate().createMessageClientFactory();
	}
	
	/**
	 * @return
	 */
	private static ServiceLocator getDelegate(){
		return (ServiceLocator)delegate;
	}
	
}
