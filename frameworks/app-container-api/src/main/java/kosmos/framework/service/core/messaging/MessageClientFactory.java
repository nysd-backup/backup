/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.messaging;

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
	 * @return the service
	 */
	public <T> T createPublisher(Class<T> serviceType);
	
	/**
	 * Creates a sender for QUEUE.
	 * @param <T> the type
	 * @param serviceType the interface of service
	 * @return the service
	 */
	public <T> T createSender(Class<T> serviceType);
}
