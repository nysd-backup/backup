/**
 * Copyright 2011 the original author
 */
package alpha.framework.messaging.client;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Session;

import alpha.framework.messaging.XmlUtils;
import alpha.framework.messaging.client.MessagingProperty;


/**
 * The JMS producer.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class XmlMessageProducer extends AbstractTxMessageProducer{
	
	/**
	 * @see alpha.framework.messaging.client.AbstractTxMessageProducer#preProduce(java.lang.Object, javax.jms.Destination, alpha.framework.messaging.client.MessagingProperty)
	 */
	@Override
	protected Object preProduce(Object data, Destination destination,MessagingProperty property) {
		return XmlUtils.marshal(data);
	}

	/**
	 * @see alpha.framework.messaging.client.AbstractTxMessageProducer#createMessage(javax.jms.Session, java.lang.Object)
	 */
	@Override
	protected Message createMessage(Session session, Object data)
			throws javax.jms.JMSException {
		return session.createTextMessage((String)data);
	}

	
}
