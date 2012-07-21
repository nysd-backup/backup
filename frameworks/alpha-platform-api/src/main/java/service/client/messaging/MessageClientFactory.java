/**
 * Copyright 2011 the original author
 */
package service.client.messaging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import service.framework.core.activation.ServiceLocator;

/**
 * The factory to create JMS producer.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageClientFactory{
	
	/**
	 * Creates a publisher for TOPIC.
	 * @param <T> the type
	 * @param serviceType the interface of service
	 * @return the service
	 */
	public <T> T createPublisher(Class<T> service) {
		InvocationHandler handler = ServiceLocator.createDefaultPublisher();
		return service.cast(Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, handler));
	}
	/**
	 * Creates a sender for QUEUE.
	 * @param <T> the type
	 * @param serviceType the interface of service
	 * @return the service
	 */
	public <T> T createSender(Class<T> service) {
		InvocationHandler handler = ServiceLocator.createDefaultSender();
		return service.cast(Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, handler));
	}

}
