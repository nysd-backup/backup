/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.registry;

import org.coder.alpha.framework.registry.ComponentFinder;
import org.springframework.context.ApplicationContext;



/**
 * The alpha.domain locator using Spring
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class SpringComponentFinder implements ComponentFinder{
	
	/** the context */
	protected ApplicationContext context = null;
	
	/**
	 * @param context
	 */
	public SpringComponentFinder(ApplicationContext context){
		this.context = context;
	}

	/**
	 * @see org.coder.alpha.framework.registry.ComponentFinder#getBean(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(String name) {
		return (T)context.getBean(name);
	}

	/**
	 * @see org.coder.alpha.framework.registry.ComponentFinder#getBean(java.lang.Class)
	 */
	@Override
	public <T> T getBean(Class<T> requiredType) {
		return (T)context.getBean(requiredType);
	}

}
