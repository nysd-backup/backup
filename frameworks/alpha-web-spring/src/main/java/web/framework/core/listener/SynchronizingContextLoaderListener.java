/**
 * Copyright 2011 the original author
 */
package web.framework.core.listener;

import javax.servlet.ServletContextEvent;


import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import service.framework.core.activation.ExternalServiceLocatorImpl;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SynchronizingContextLoaderListener extends ContextLoaderListener{
	
	private ExternalServiceLocatorImpl locator = null;

	/**
	 * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		
		locator = new ExternalServiceLocatorImpl();
		locator.construct(WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()));
	}
	
	/**
	 * @see org.springframework.web.context.ContextLoaderListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		if(locator != null){
			locator.destroy();
			locator = null;
		}
	}


}
