/**
 * Copyright 2011 the original author
 */
package alpha.framework.messaging.client;


/**
 * The factory to create JMS producer.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface MessageClientFactory{
	
	/**
	 * Creates a publisher for TOPIC.
	 * @param <T> the type
	 * @param serviceType the interface of alpha.domain
	 * @param property the messaging hint
	 * @return the alpha.domain
	 */
	<T> T createPublisher(Class<T> service,MessagingProperty property);

	/**
	 * Creates a sender for QUEUE.
	 * @param <T> the type
	 * @param serviceType the interface of alpha.domain
	 * @return the alpha.domain
	 */
	<T> T createSender(Class<T> service,MessagingProperty property);
	
}
