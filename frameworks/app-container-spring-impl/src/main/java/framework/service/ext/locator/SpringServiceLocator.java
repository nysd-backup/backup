/**
 * Use is subject to license terms.
 */
package framework.service.ext.locator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import framework.service.core.locator.ServiceLocator;

/**
 * Spring管理のサービスロケータ.
 *
 * @author	yoshida-n
 * @version	2010/12/30 new create
 */
public abstract class SpringServiceLocator extends ServiceLocator{
	
	/** コンテキスト */
	protected ApplicationContext context = null;	

	/**
	 * 初期処理
	 */
	public abstract void construct();
	
	/**
	 * 終了処理
	 */
	public abstract void destroy();
	
	/**
	 * 初期処理
	 */
	protected void initialize(String classpathResource) {
		context = new ClassPathXmlApplicationContext(classpathResource);
		delegate = this;
	}
	
	/**
	 * 終了処理
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
		return clazz.cast(lookupService(StringUtils.uncapitalize(clazz.getSimpleName())+ "Impl"));
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
