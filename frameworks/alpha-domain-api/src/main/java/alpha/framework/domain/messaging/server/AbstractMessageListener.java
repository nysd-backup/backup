/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.messaging.server;

import java.lang.reflect.Method;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import alpha.framework.domain.activation.ServiceLocator;
import alpha.framework.domain.messaging.client.AbstractMessageProducer;

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
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractMessageListener implements MessageListener{


	/**
	 * @param arg0 the message
	 */
	public void onMessage(Message arg0) {
			
		try{
			invoke(arg0);	
		} catch (Throwable e) {
			handleThrowable(e);
		}				
	}
	
	/**
	 * Invokes the alpha.domain.
	 * @param dto the dto 
	 */
	protected Object invoke(Message message) throws Throwable{
		
		String serviceName = message.getStringProperty(AbstractMessageProducer.SERVICE_NAME);
		String methodName = message.getStringProperty(AbstractMessageProducer.METHOD_NAME);

		Object service = ServiceLocator.getService(serviceName);				
		Method[] ms = service.getClass().getDeclaredMethods();
		Method target = null;
		for(Method m : ms){
			if(m.equals(methodName)){
				target = m;
			}
		}	
		return target.invoke(service, getArguments(message,target.getParameterTypes()[0]));
	}
	
	/**
	 * Gets the arguments.
	 * @param message the message
	 * @return
	 */
	protected abstract Object getArguments(Message message,Class<?> type) throws JMSException;
	
	/**
	 * Handles the exceptions and errors.
	 * @param notifier the notifier
	 * @param e the exception
	 */
	protected void handleThrowable(Throwable e){		
		if(e instanceof Error){
			throw Error.class.cast(e);
		}else if(e instanceof RuntimeException){
			throw RuntimeException.class.cast(e);
		}
		throw new IllegalStateException(e);
	}
}
