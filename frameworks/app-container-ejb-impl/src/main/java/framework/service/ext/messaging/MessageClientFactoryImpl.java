/**
 * Use is subject to license terms.
 */
package framework.service.ext.messaging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import framework.service.core.messaging.MessageClientFactory;
import framework.service.ext.locator.ServiceLocatorImpl;


/**
 * メッセージングクライアントのファクトリ.
 *
 * @author yoshida-n
 * @version	2011/05/07 created.
 */
public class MessageClientFactoryImpl implements MessageClientFactory{

	/**
	 * @see framework.service.core.messaging.MessageClientFactory#createPublisher(java.lang.Class)
	 */
	@Override
	public <T> T createPublisher(Class<T> service) {
		InvocationHandler handler = ServiceLocatorImpl.getComponentBuilder().createPublisher();
		return service.cast(Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, handler));
	}
	
	/**
	 * @see framework.service.core.messaging.MessageClientFactory#createSender(java.lang.Class)
	 */
	@Override
	public <T> T createSender(Class<T> service) {
		InvocationHandler handler = ServiceLocatorImpl.getComponentBuilder().createSender();
		return service.cast(Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, handler));
	}

}
