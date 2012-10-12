/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.messaging.client;

import java.io.Serializable;

import javax.jms.Message;
import javax.jms.Session;


/**
 * The JMS producer.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ObjectMessageProducer extends AbstractTxMessageProducer{

	/**
	 * @see alpha.framework.domain.messaging.client.AbstractTxMessageProducer#createMessage(javax.jms.Session, java.lang.Object)
	 */
	@Override
	protected Message createMessage(Session session, Object data)
			throws javax.jms.JMSException {
		return session.createObjectMessage((Serializable)data);
	}

	
}
