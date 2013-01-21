/**
 * Copyright 2011 the original author
 */
package alpha.framework.messaging.server;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;



/**
 * A object message listener for MDB and MDP.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class ObjectMessageListener extends AbstractMessageListener{

	/**
	 * @see alpha.framework.messaging.server.AbstractMessageListener#getArguments(javax.jms.Message)
	 */
	@Override
	protected Object getArguments(Message message,Class<?> type) throws JMSException{
		ObjectMessage object = ObjectMessage.class.cast(message);	
		return object.getObject();
	}
}