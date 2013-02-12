package alpha.domain.spring.example;

import javax.servlet.ServletContextEvent;

import org.coder.alpha.framework.registry.ServiceLocatorInitializer;
import org.coder.alpha.framework.registry.SpringComponentFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener{

	/**
	 * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		SpringComponentFinder finder = new SpringComponentFinder(context);
		ServiceLocatorInitializer initializer = new ServiceLocatorInitializer();
		initializer.initiazie(finder);
	}
	
	/**
	 * @see org.springframework.web.context.ContextLoaderListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		ServiceLocatorInitializer initializer = new ServiceLocatorInitializer();
		initializer.initiazie(null);
	}
}
