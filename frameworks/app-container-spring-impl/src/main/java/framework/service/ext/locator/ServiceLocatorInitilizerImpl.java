/**
 * Use is subject to license terms.
 */
package framework.service.ext.locator;

import org.springframework.context.ApplicationContext;

import framework.api.service.ServiceLocatorInitializer;

/**
 * Webに連動したサービスロケータの初期化.
 *
 * @author yoshida-n
 * @version	2011/05/11 created.
 */
public class ServiceLocatorInitilizerImpl implements ServiceLocatorInitializer{

	private SpringServiceLocator locater = null;

	/**
	 * @see framework.api.service.ServiceLocatorInitializer#construct(java.lang.Object)
	 */
	@Override
	public void construct(Object resource) {
		if( resource instanceof String){
			locater = new ServiceLocatorImpl();
			((ServiceLocatorImpl)locater).construct((String)resource);
		}else if(resource instanceof ApplicationContext){
			locater = new ExternalServiceLocatorImpl();
			((ExternalServiceLocatorImpl)locater).construct((ApplicationContext)resource);
		}
	}

	/**
	 * @see framework.api.service.ServiceLocatorInitializer#destroy()
	 */
	@Override
	public void destroy() {
		if( locater != null){
			locater.destroy();
		}
	}
}
