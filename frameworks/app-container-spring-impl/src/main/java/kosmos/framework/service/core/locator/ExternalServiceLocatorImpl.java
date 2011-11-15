/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.locator;

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
	 * @see kosmos.framework.service.core.locator.SpringServiceLocator#construct()
	 */
	@Override
	public void construct() {		
		throw new UnsupportedOperationException();
	}

	/**
	 * @see kosmos.framework.service.core.locator.SpringServiceLocator#destroy()
	 */
	@Override
	public void destroy() {
		context = null;
		delegate = null;
	}

}