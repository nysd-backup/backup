package alpha.framework.domain.messaging.client;


/**
 * Default messaging client factory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class XmlMessageClientFactoryProvider implements MessageClientFactoryProvider{

	/**
	 * @see alpha.framework.domain.messaging.client.MessageClientFactoryProvider#getMessageClientFactory()
	 */
	@Override
	public MessageClientFactory getMessageClientFactory() {
		MessageClientFactoryImpl impl = new MessageClientFactoryImpl();
		impl.setQueueProducer(new XmlMessageProducer());
		impl.setTopicProducer(new XmlMessageProducer());
		return impl;
	}

}
