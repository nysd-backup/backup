/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.service;

import kosmos.framework.core.context.AbstractContainerContext;



/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class ComponentLocator {
	
	/** the delegating locator */
	protected static ComponentLocator delegate = null;
	
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
	 * 
	 * @return the context
	 */
	public abstract <T extends AbstractContainerContext> T createContext();
	
	/**
	 * Creates the context.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends AbstractContainerContext> T createContainerContext(){
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
