/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.messaging;

import javax.jms.JMSException;



/**
 * The JMS producer.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface JmsProducer {

	/**
	 * Send the message to QUEUE.
	 * @param dto the DTO
	 * @param destinationName the name of destination
	 * @throws JMSException the exception
	 */
	public void send(InvocationParameter dto ,String destinationName) throws JMSException;
	
	
	/**
	 * Publish the message to TOPIC.
	 * @param dto the DTO
	 * @param destinationName the name of destination
	 * @throws JMSException the exception
	 */
	public void publish(InvocationParameter dto ,String destinationName) throws JMSException;
}
