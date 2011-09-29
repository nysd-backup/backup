/**
 * Copyright 2011 the original author
 */
package framework.service.ext.locator;

import org.springframework.context.ApplicationContext;


/**
 * Holding the context initialized at the external container.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ExternalServiceLocatorImpl extends SpringServiceLocator{

	/**
	 * @param context the initialized context
	 */
	public void construct(ApplicationContext context) {	
		this.context = context;
		delegate = this;
	}
	
	/**
	 * @see framework.service.ext.locator.SpringServiceLocator#construct()
	 */
	@Override
	public void construct() {		
		throw new UnsupportedOperationException();
	}

	/**
	 * @see framework.service.ext.locator.SpringServiceLocator#destroy()
	 */
	@Override
	public void destroy() {
		context = null;
		delegate = null;
	}

}
