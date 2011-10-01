/**
 * Copyright 2011 the original author
 */
package framework.web.core.loader;


/**
 * Finds the component .
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ComponentLocator {

	/** the singleton delegate */
	protected static ComponentLocator delegate = null;
	
	/**
	 * Look up service by interface.
	 * 
	 * @param <T>　the type
	 * @param clazz the class
	 * @return the service
	 */
	public abstract <T> T lookupServiceByInterface(Class<T> clazz);
	
	/**
	 * Look up service by name.
	 * 
	 * @param name the name
	 * @return the service
	 */
	public abstract <T> T lookupService(String name);
	
	/**
	 * Look up service by interface.
	 * 
	 * @param <T>　the type
	 * @param clazz the class
	 * @return the service
	 */
	public static <T> T lookupByInterface(Class<T> ifType){
		return delegate.lookupServiceByInterface(ifType);
	}
	
	/**
	 * Look up service by name.
	 * 
	 * @param <T> the type
	 * @param name the name of the service
	 * @return the service
	 */
	@SuppressWarnings("unchecked")
	public static <T> T lookup(String name){
		return (T)delegate.lookupService(name);
	}

}
