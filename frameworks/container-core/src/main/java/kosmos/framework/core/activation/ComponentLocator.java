/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.activation;


/**
 * Creates the components.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class ComponentLocator {
	
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
	
	
}
