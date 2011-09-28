/**
 * Copyright 2011 the original author
 */
package framework.service.core.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import framework.api.dto.RequestDto;
import framework.api.service.RequestListener;

/**
 * A listener for MDB and MDP.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractMessageListener implements MessageListener{

	/**
	 * @param arg0 the message
	 */
	public void onMessage(Message arg0) {

		ObjectMessage message = ObjectMessage.class.cast(arg0);
		RequestDto dto  = null;
		try{
			dto = RequestDto.class.cast( message.getObject());
		}catch(JMSException jmse){
			throw new IllegalStateException(jmse);
		}
		RequestListener listener = createListener();
		listener.processService(dto);
	}
	
	/**
	 * @return the listener
	 */
	protected abstract RequestListener createListener();
}
