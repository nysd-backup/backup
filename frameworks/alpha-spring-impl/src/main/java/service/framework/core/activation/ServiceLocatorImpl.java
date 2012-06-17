/**
 * Copyright 2011 the original author
 */
package service.framework.core.activation;


/**
 * Initialize the new context for AP container.
 * 
 * <pre>
 * Use this to separate the context between WEB and AP
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceLocatorImpl extends SpringServiceLocator{

	/**
	 * @param resource the external resource
	 */
	public void construct(String resource) {		
		initialize(resource);
	}
	
	/**
	 * @see service.framework.core.activation.SpringServiceLocator#construct()
	 */
	@Override
	public void construct() {		
		throw new UnsupportedOperationException();
	}

	/**
	 * @see service.framework.core.activation.SpringServiceLocator#destroy()
	 */
	@Override
	public void destroy() {
		terminate();
	}



}
