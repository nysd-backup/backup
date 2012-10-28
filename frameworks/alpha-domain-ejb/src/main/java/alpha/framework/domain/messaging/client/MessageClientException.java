/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.messaging.client;

import javax.jms.JMSException;

/**
 * Exception for JMS producer.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessageClientException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param e the JMSException 
	 */
	public MessageClientException(JMSException e){
		super(e);
	}

}
