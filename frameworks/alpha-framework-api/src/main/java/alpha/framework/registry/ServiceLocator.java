/**
 * Copyright 2011 the original author
 */
package alpha.framework.registry;



/**
 * ServiceLocator.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public final class ServiceLocator{
	
	/** the ComponentFinder */
	private static ComponentFinder finder;
	
	/**
	 * @param componentFinder the componentFinder
	 */
	static void setComponentFinder(ComponentFinder componentFinder){
		finder = componentFinder;
	}
	
	/**
	 * @param serviceName the serviceName to lookup
	 * @return the alpha.domain
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getService(String serviceName){
		return (T)finder.getBean(serviceName);
	}
	
	/**
	 * @param serviceType the serviceType to lookup
	 * @return the alpha.domain
	 */
	public static <T> T getService(Class<T> serviceType){
		return finder.getBean(serviceType);
	}

	/**
	 * @return ComponentFinder
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ComponentFinder> T getComponentFinder() {
		return (T)finder;
	}
}
