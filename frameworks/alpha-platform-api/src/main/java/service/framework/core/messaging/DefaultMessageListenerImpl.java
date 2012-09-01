/**
 * Copyright 2011 the original author
 */
package service.framework.core.messaging;

import java.lang.reflect.Method;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import service.client.messaging.AbstractMessageProducer;
import service.framework.core.activation.ServiceLocator;
import core.logics.log.FaultNotifier;
import core.message.MessageLevel;


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
public class DefaultMessageListenerImpl implements MessageListener{

	/**
	 * @param arg0 the message
	 */
	public void onMessage(Message arg0) {
		
		FaultNotifier notifier = ServiceLocator.createDefaultFaultNotifier();		
		try{
			invoke(arg0);	
		} catch (Throwable e) {
			handleThrowable(e,notifier);
		}				
	}
	
	/**
	 * Invokes the service.
	 * @param dto the dto 
	 */
	protected Object invoke(Message message) throws Throwable{
		
		String serviceName = message.getStringProperty(AbstractMessageProducer.SERVICE_NAME);
		String methodName = message.getStringProperty(AbstractMessageProducer.METHOD_NAME);
		String[] parameterTypeName = (String[])message.getObjectProperty(AbstractMessageProducer.PARAMETER_TYPE_NAME);

		Object service = ServiceLocator.getService(serviceName);				
		Method m = null;
		if(parameterTypeName == null){
			m = service.getClass().getMethod(methodName);
		}else {
			Class<?>[] clss = new Class[parameterTypeName.length];
			for(int i = 0 ; i< clss.length; i++){
				clss[i] = Class.forName(parameterTypeName[i]);
			}
			m = service.getClass().getMethod(methodName,clss);
		}		
		
		ObjectMessage object = ObjectMessage.class.cast(message);		
		return m.invoke(service, object.getObject());
	}
	
	/**
	 * Handles the exceptions and errors.
	 * @param notifier the notifier
	 * @param e the exception
	 */
	protected void handleThrowable(Throwable e,FaultNotifier notifier){
		notifyException(e,notifier);	
		if(e instanceof Error){
			throw Error.class.cast(e);
		}else if(e instanceof RuntimeException){
			throw RuntimeException.class.cast(e);
		}
		throw new IllegalStateException(e);
	}
	
	/**
	 * Notifies exception to the agent.
	 * @param t the exception 
	 * @param notifier the notifier
	 */
	protected void notifyException(Throwable t ,FaultNotifier notifier){		
		notifier.notify("99", "unknown error : " + t.getMessage(), MessageLevel.F.ordinal());
	}	
}
