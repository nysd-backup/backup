/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.messaging;

import kosmos.framework.core.dto.CompositeRequest;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.messaging.AbstractMessageProducer;

/**
 * The producer for QUEUE.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueueProducerDelegate extends AbstractMessageProducer{

	/**
	 * @see kosmos.framework.service.core.messaging.AbstractMessageProducer#invoke(kosmos.framework.core.dto.CompositeRequest, java.lang.String)
	 */
	@Override
	protected Object invoke(CompositeRequest dto, String destinationName)
			throws Throwable {
		
		ServiceLocator.lookupByInterface(JmsProducer.class).send(dto, destinationName);
		return null;
	}

}
