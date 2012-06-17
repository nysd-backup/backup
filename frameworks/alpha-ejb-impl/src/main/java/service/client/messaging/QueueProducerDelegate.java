/**
 * Copyright 2011 the original author
 */
package service.client.messaging;

import service.client.messaging.AbstractMessageProducer;
import service.client.messaging.InvocationParameter;
import service.framework.core.activation.ServiceLocator;

/**
 * The producer for QUEUE.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueueProducerDelegate extends AbstractMessageProducer{

	/**
	 * @see service.client.messaging.AbstractMessageProducer#invoke(service.client.messaging.InvocationParameter, java.lang.String)
	 */
	@Override
	protected Object invoke(InvocationParameter dto, String destinationName)
			throws Throwable {
		
		ServiceLocator.lookupByInterface(JmsProducer.class).send(dto, destinationName);
		return null;
	}

}
