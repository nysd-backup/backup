/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.messaging.client;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Session;

import org.coder.alpha.framework.messaging.XmlUtils;
import org.coder.alpha.framework.messaging.client.MessagingProperty;



/**
 * The JMS producer.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class XmlMessageProducer extends AbstractTxMessageProducer{
	
	/**
	 * @see org.coder.alpha.framework.messaging.client.AbstractTxMessageProducer#preProduce(java.lang.Object, javax.jms.Destination, org.coder.alpha.framework.messaging.client.MessagingProperty)
	 */
	@Override
	protected Object preProduce(Object data, Destination destination,MessagingProperty property) {
		return XmlUtils.marshal(data);
	}

	/**
	 * @see org.coder.alpha.framework.messaging.client.AbstractTxMessageProducer#createMessage(javax.jms.Session, java.lang.Object)
	 */
	@Override
	protected Message createMessage(Session session, Object data)
			throws javax.jms.JMSException {
		return session.createTextMessage((String)data);
	}

	
}
