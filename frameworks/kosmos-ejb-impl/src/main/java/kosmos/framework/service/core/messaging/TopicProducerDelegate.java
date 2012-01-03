/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.messaging;

import kosmos.framework.core.dto.RequestDto;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.messaging.AbstractMessageProducer;

/**
 * The producer for TOPIC.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class TopicProducerDelegate extends AbstractMessageProducer{

	/**
	 * @see kosmos.framework.service.core.messaging.AbstractMessageProducer#invoke(kosmos.framework.core.dto.RequestDto, java.lang.String)
	 */
	@Override
	protected Object invoke(RequestDto dto, String destinationName)
			throws Throwable {
		
		ServiceLocator.lookupByInterface(JmsProducer.class).publish(dto, destinationName);
		return null;
	}

}
