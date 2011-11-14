/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.messaging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import kosmos.framework.service.core.locator.ServiceLocator;
import kosmos.framework.service.core.messaging.MessageClientFactory;


/**
 * The factory to create the JMS producer.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageClientFactoryImpl implements MessageClientFactory{

	/**
	 * @see kosmos.framework.service.core.messaging.MessageClientFactory#createPublisher(java.lang.Class)
	 */
	@Override
	public <T> T createPublisher(Class<T> service) {
		InvocationHandler handler = ServiceLocator.createDefaultPublisher();
		return service.cast(Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, handler));
	}
	
	/**
	 * @see kosmos.framework.service.core.messaging.MessageClientFactory#createSender(java.lang.Class)
	 */
	@Override
	public <T> T createSender(Class<T> service) {
		InvocationHandler handler = ServiceLocator.createDefaultSender();
		return service.cast(Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, handler));
	}

}
