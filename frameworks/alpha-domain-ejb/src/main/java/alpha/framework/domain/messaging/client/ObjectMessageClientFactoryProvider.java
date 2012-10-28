package alpha.framework.domain.messaging.client;

import alpha.framework.domain.messaging.client.MessageClientFactory;

/**
 * Default messaging client factory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ObjectMessageClientFactoryProvider implements MessageClientFactoryProvider{

	/**
	 * @see alpha.framework.domain.messaging.client.MessageClientFactoryProvider#getMessageClientFactory()
	 */
	@Override
	public MessageClientFactory getMessageClientFactory() {
		MessageClientFactoryImpl impl = new MessageClientFactoryImpl();
		impl.setQueueProducer(new ObjectMessageProducer());
		impl.setTopicProducer(new ObjectMessageProducer());
		return impl;
	}

}
