package alpha.framework.registry;

import alpha.framework.messaging.client.MessageClientFactory;
import alpha.framework.messaging.client.MessageClientFactoryImpl;
import alpha.framework.messaging.client.ObjectMessageProducer;

/**
 * Default messaging client factory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ObjectMessageClientFactoryFiinder implements MessageClientFactoryFinder{

	/**
	 * @see alpha.framework.registry.MessageClientFactoryFinder#getMessageClientFactory()
	 */
	@Override
	public MessageClientFactory getMessageClientFactory() {
		MessageClientFactoryImpl impl = new MessageClientFactoryImpl();
		impl.setQueueProducer(new ObjectMessageProducer());
		impl.setTopicProducer(new ObjectMessageProducer());
		return impl;
	}

}
