package org.coder.alpha.framework.registry;

import org.coder.alpha.framework.messaging.client.MessageClientFactory;
import org.coder.alpha.framework.messaging.client.MessageClientFactoryImpl;
import org.coder.alpha.framework.messaging.client.XmlMessageProducer;



/**
 * Default messaging client factory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class XmlMessageClientFactoryFinder implements MessageClientFactoryFinder{

	/**
	 * @see org.coder.alpha.framework.registry.MessageClientFactoryFinder#getMessageClientFactory()
	 */
	@Override
	public MessageClientFactory getMessageClientFactory() {
		MessageClientFactoryImpl impl = new MessageClientFactoryImpl();
		impl.setQueueProducer(new XmlMessageProducer());
		impl.setTopicProducer(new XmlMessageProducer());
		return impl;
	}

}
