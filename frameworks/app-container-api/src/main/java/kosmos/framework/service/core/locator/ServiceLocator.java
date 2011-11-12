/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.locator;

import kosmos.framework.service.core.transaction.ServiceContext;


/**
 * A service locator.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceLocator {
	
	/** the delegating locator */
	protected static ServiceLocator delegate = null;
	
	/**
	 * Creates the context.
	 * 
	 * @return the context
	 */
	public abstract ServiceContext createContext();
	
	/**
	 * Look up service using interface.
	 * @param <T>　the type
	 * @param clazz the interface of target service
	 * @return the service
	 */
	public abstract <T> T lookupServiceByInterface(Class<T> clazz);
	
	/**
	 * Look up service using name.
	 * @param name the name of service
	 * @return the service
	 */
	public abstract <T> T lookupService(String name);
	
	/**
	 * look up remote service.
	 * @param <T>　the type
	 * @param clazz the interface of target service
	 * @return the service
	 */
	public abstract <T> T lookupRemoteService(Class<T> clazz);
	
	/**
	 * Creates the context.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ServiceContext> T createContainerContext(){
		return (T)delegate.createContext();
	}
		
	/**
	 * Look up service using interface.
	 * @param <T> the type
	 * @param ifType the interface of target service
	 * @return the service
	 */
	public static <T> T lookupByInterface(Class<T> ifType){
		return delegate.lookupServiceByInterface(ifType);
	}
	
	/**
	 * Look up service using name.
	 * @param <T> the type
	 * @param name the name of target service
	 * @return the service
	 */
	@SuppressWarnings("unchecked")
	public static <T> T lookup(String name){
		return (T)delegate.lookupService(name);
	}
	
	
	/**
	 * Look up remote service using interface.
	 * @param <T> the type
	 * @param clazz the interface of target service
	 * @return the service
	 */
	public static <T> T lookupRemote(Class<T> clazz){
		return delegate.lookupRemoteService(clazz);
	}
	
}
