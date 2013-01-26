/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.messaging.server;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.coder.alpha.framework.messaging.XmlUtils;




/**
 * A text message listener for MDB and MDP.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class XmlMessageListener extends AbstractMessageListener{

	/**
	 * @see org.coder.alpha.framework.messaging.server.AbstractMessageListener#getArguments(javax.jms.Message)
	 */
	@Override
	protected Object getArguments(Message message,Class<?> type) throws JMSException{
		TextMessage object = TextMessage.class.cast(message);	
		return XmlUtils.unmarshal(object.getText(),type);
	}
}