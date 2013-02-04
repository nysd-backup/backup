package org.coder.alpha.framework.registry;

import org.coder.alpha.framework.messaging.client.MessageClientFactory;
import org.coder.alpha.framework.messaging.client.DefaultMessageClientFactory;
import org.coder.alpha.framework.messaging.client.ObjectMessageProducer;


/**
 * Default messaging client factory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ObjectMessageClientFactoryFiinder implements MessageClientFactoryFinder{

	/**
	 * @see org.coder.alpha.framework.registry.MessageClientFactoryFinder#getMessageClientFactory()
	 */
	@Override
	public MessageClientFactory getMessageClientFactory() {
		DefaultMessageClientFactory impl = new DefaultMessageClientFactory();
		impl.setQueueProducer(new ObjectMessageProducer());
		impl.setTopicProducer(new ObjectMessageProducer());
		return impl;
	}

}
