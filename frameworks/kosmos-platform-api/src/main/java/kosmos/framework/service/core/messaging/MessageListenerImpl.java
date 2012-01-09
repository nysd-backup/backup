/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import kosmos.framework.core.activation.ServiceActivator;
import kosmos.framework.core.dto.CompositeRequest;
import kosmos.framework.service.core.activation.ServiceLocator;

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
		CompositeRequest dto  = null;
		try{
			dto = CompositeRequest.class.cast( message.getObject());
		}catch(JMSException jmse){
			throw new IllegalStateException(jmse);
		}
		ServiceActivator activator = ServiceLocator.createDefaultServiceActivator();
		try {
			activator.activate(dto);
		} catch (Throwable e) {
			if(e instanceof Error){
				throw Error.class.cast(e);
			}else if(e instanceof RuntimeException){
				throw RuntimeException.class.cast(e);
			}
			throw new IllegalStateException(e);
		}
	}
	
}
