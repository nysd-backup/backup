/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.loader;

import javax.servlet.ServletContextEvent;

import kosmos.framework.api.service.ServiceLocatorInitializer;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Synchronizes the WebApplicationContext to APP-container's ApplicationContext.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SynchronizingContextLoaderListener extends ContextLoaderListener{

	private ServiceLocatorInitializer initializer;
	
	/**
	 * @see org.springframework.web.context.ContextLoader#initWebApplicationContext(javax.servlet.ServletContext)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);	
		
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		
		//WEB層用
		ComponentLocatorImpl webAppLocator = new ComponentLocatorImpl();
		webAppLocator.construct(context);
		
		//ビジネスロジック層用
		initializer = context.getBean(ServiceLocatorInitializer.class);
		String contextName = event.getServletContext().getInitParameter("serviceContext");
		if( contextName == null || contextName.isEmpty()){
			initializer.construct(context);
		}else{
			initializer.construct(contextName);
		}
	}
	
	/**
	 * @see org.springframework.web.context.ContextLoader#closeWebApplicationContext(javax.servlet.ServletContext)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try{
			ComponentLocatorImpl.terminate();
		}finally{
			try{
				if(initializer != null){
					initializer.destroy();
					initializer = null;
				}
			}finally{
				super.contextDestroyed(event) ; 
			}
		}
	}
}
