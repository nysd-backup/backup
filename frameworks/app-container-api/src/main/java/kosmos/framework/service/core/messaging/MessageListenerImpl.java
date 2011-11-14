/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import kosmos.framework.api.dto.RequestDto;
import kosmos.framework.api.service.ServiceActivator;
import kosmos.framework.service.core.locator.ServiceLocator;

/**
 * A listener for MDB and MDP.
 *
 * <pre>
 * the properties of <code>@ActivationConfigProperty</code> is under.
 *
 * destinationType 
 *   - this is either Topic or Queue
 * connectionFactoryJndiName 
 *   - specifies the JNDI name of the connection factory that should create the JMS connection
 * destinationName 
 *   - specifies that we are listening for messages arriving at a destination with the JNDI name
 * </pre>
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageListenerImpl implements MessageListener{

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
		ServiceActivator activator = createListener();
		activator.activateAndInvoke(dto);
	}
	
	/**
	 * @return the ServiceActivator
	 */
	protected ServiceActivator createListener(){
		return ServiceLocator.createDefaultServiceActivator();
	}
}
