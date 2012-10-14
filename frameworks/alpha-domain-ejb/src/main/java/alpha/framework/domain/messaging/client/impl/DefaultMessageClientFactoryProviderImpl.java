package alpha.framework.domain.messaging.client.impl;

import alpha.framework.domain.messaging.client.MessageClientFactory;
import alpha.framework.domain.messaging.client.MessageClientFactoryProvider;
import alpha.framework.domain.messaging.client.ObjectMessageProducer;

/**
 * Default messaging client factory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultMessageClientFactoryProviderImpl implements MessageClientFactoryProvider{

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
