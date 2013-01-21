package alpha.framework.registry;

import alpha.framework.messaging.client.MessageClientFactory;
import alpha.framework.messaging.client.MessageClientFactoryImpl;
import alpha.framework.messaging.client.XmlMessageProducer;


/**
 * Default messaging client factory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class XmlMessageClientFactoryFinder implements MessageClientFactoryFinder{

	/**
	 * @see alpha.framework.registry.MessageClientFactoryFinder#getMessageClientFactory()
	 */
	@Override
	public MessageClientFactory getMessageClientFactory() {
		MessageClientFactoryImpl impl = new MessageClientFactoryImpl();
		impl.setQueueProducer(new XmlMessageProducer());
		impl.setTopicProducer(new XmlMessageProducer());
		return impl;
	}

}
