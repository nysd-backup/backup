/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.messaging;

import java.lang.reflect.Proxy;

import javax.jms.JMSContext;

import org.coder.alpha.framework.registry.ComponentFinder;


/**
 * The factory to create JMS producer.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageClientFactory{

	/**
	 * @param service
	 * @param property
	 * @return
	 */
	public <T> T createProducer(Class<T> service,MessagingProperty property , ComponentFinder finder , JMSContext context) {		
		MessageInvocationHandler handler = new MessageInvocationHandler(property,context,finder);
		return service.cast(Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, handler));
	}

}
