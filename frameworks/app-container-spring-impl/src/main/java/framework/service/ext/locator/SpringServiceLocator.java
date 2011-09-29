/**
 * Copyright 2011 the original author
 */
package framework.service.ext.locator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import framework.service.core.locator.ServiceLocator;

/**
 * The service locator using Spring
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class SpringServiceLocator extends ServiceLocator{
	
	/** the context */
	protected ApplicationContext context = null;	

	/**
	 * Initialize the context.
	 */
	public abstract void construct();
	
	/**
	 * Terminate the context.
	 */
	public abstract void destroy();
	
	/**
	 * Initialize the context.
	 * @param classpathResource the resource
	 */
	protected void initialize(String classpathResource) {
		context = new ClassPathXmlApplicationContext(classpathResource);
		delegate = this;
	}
	
	/**
	 * Terminate the context.
	 */
	protected void terminate(){		
		if(context != null){
			((ClassPathXmlApplicationContext)context).destroy();
			context = null;
		}
		delegate = null;
	}

	/**
	 * @see framework.service.core.locator.ServiceLocator#lookupServiceByInterface(java.lang.Class)
	 */
	@Override
	public <T> T lookupServiceByInterface(Class<T> clazz) {
		return clazz.cast(context.getBean(clazz));
	}

	/**
	 * @see framework.service.core.locator.ServiceLocator#lookupService(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupService(String name) {
		return (T)context.getBean(name);
	}

	/**
	 * @see framework.service.core.locator.ServiceLocator#lookupRemoteService(java.lang.Class)
	 */
	@Override
	public <T> T lookupRemoteService(Class<T> serviceType){		
		throw new UnsupportedOperationException();
	}
	
	
}
