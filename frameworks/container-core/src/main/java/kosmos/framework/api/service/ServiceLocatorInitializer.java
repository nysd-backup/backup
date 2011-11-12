/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.service;

/**
 * The context initializer 
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ServiceLocatorInitializer {
	
	/**
	 * Initializes the context.
	 * 
	 * @param context the context
	 */
	public void construct(Object context);
	
	/**
	 * Destroys the context.
	 */
	public void destroy();
}
