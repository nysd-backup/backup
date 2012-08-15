/**
 * Copyright 2011 the original author
 */
package web.framework.core.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import service.framework.core.activation.ServiceLocatorImpl;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SynchronizingContextLoaderListener extends ContextLoaderListener{
	
	/**
	 * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		
		ServiceLocatorImpl locator = new ServiceLocatorImpl(WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()));
		ServiceLocatorImpl.setDelegate(locator);
	}
	
	/**
	 * @see org.springframework.web.context.ContextLoaderListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		ServiceLocatorImpl.setDelegate(null);	
		super.contextDestroyed(event);	
	}

}
