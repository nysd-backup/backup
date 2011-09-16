/**
 * Use is subject to license terms.
 */
package framework.web.core.loader;

import javax.servlet.ServletContextEvent;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import framework.api.service.ServiceLocatorInitializer;

/**
 * サービスコンテナのApplicationContextの初期化をWEBの初期化と同期化させる.
 *
 * @author yoshida-n
 * @version	2011/05/11 created.
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
