package org.coder.alpha.framework.registry;

import org.coder.alpha.framework.messaging.DefaultMessageClientFactory;
import org.coder.alpha.framework.messaging.MessageClientFactory;
import org.coder.alpha.framework.messaging.XmlMessageProducer;



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
		DefaultMessageClientFactory impl = new DefaultMessageClientFactory();
		impl.setQueueProducer(new XmlMessageProducer());
		impl.setTopicProducer(new XmlMessageProducer());
		return impl;
	}

}
