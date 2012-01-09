/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.activation;

import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.logics.log.FaultNotifier;
import kosmos.framework.core.logics.message.MessageBuilder;



/**
 * Creates the components.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class ComponentLocator {
	
	/** the ComponentLocator */
	protected static ComponentLocator  delegate;
	
	/**
	 * Look up service using interface.
	 * @param <T>　the type
	 * @param clazz the interface of target service
	 * @return the service
	 */
	public abstract <T> T lookupComponentByInterface(Class<T> clazz);
	
	/**
	 * Look up service using name.
	 * @param name the name of service
	 * @return the service
	 */
	public abstract <T> T lookupComponent(String name);
	
	/**
	 * look up remote service.
	 * @param <T>　the type
	 * @param clazz the interface of target service
	 * @return the service
	 */
	public abstract <T> T lookupRemoteComponent(Class<T> clazz);

	/**
	 * @return the message builder
	 */
	public abstract MessageBuilder createMessageBuilder();	
	
	/**
	 * @return the fault notifier
	 */
	public abstract FaultNotifier createFaultNotifier();	
	
	/**
	 * @return the BusinessException
	 */
	public abstract BusinessException createBusinessException();
	
	/**
	 * Look up service using interface.
	 * @param <T> the type
	 * @param ifType the interface of target service
	 * @return the service
	 */
	public static <T> T lookupByInterface(Class<T> ifType){
		return delegate.lookupComponentByInterface(ifType);
	}
	
	/**
	 * Look up service using name.
	 * @param <T> the type
	 * @param name the name of target service
	 * @return the service
	 */
	@SuppressWarnings("unchecked")
	public static <T> T lookup(String name){
		return (T)delegate.lookupComponent(name);
	}
	
	/**
	 * Look up remote service using interface.
	 * @param <T> the type
	 * @param clazz the interface of target service
	 * @return the service
	 */
	public static <T> T lookupRemote(Class<T> clazz){
		return delegate.lookupRemoteComponent(clazz);
	}
	
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
	public static FaultNotifier createDefaultFaultNotifier(){
		return delegate.createFaultNotifier();
	}
	
}
