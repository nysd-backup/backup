/**
 * Copyright 2011 the original author
 */
package service.client.messaging;


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
	 * @param serviceType the interface of service
	 * @param property the messaging hint
	 * @return the service
	 */
	<T> T createPublisher(Class<T> service,MessagingProperty property);

	/**
	 * Creates a sender for QUEUE.
	 * @param <T> the type
	 * @param serviceType the interface of service
	 * @return the service
	 */
	<T> T createSender(Class<T> service,MessagingProperty property);
	
}
