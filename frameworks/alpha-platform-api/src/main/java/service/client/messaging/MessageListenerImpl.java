/**
 * Copyright 2011 the original author
 */
package service.client.messaging;

import java.lang.reflect.Method;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import core.logics.log.FaultNotifier;
import core.message.MessageLevel;

import service.framework.core.activation.ServiceLocator;


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
		
		FaultNotifier notifier = ServiceLocator.createDefaultFaultNotifier();
		
		InvocationParameter dto  = null;
		try{
			ObjectMessage message = ObjectMessage.class.cast(arg0);		
			dto = InvocationParameter.class.cast( message.getObject());
		}catch(Throwable jmse){
			notifyException(jmse,notifier);
			throw new IllegalStateException(jmse);
		}

		try{
			invoke(dto);	
		} catch (Throwable e) {
			handleThrowable(e,notifier);
		}				
	}
	
	/**
	 * Invokes the service.
	 * @param dto the dto 
	 */
	protected Object invoke(InvocationParameter dto) throws Throwable{

		Object service = ServiceLocator.getService(dto.getServiceName());				
		Method m = null;
		if(dto.getParameterTypeNames() == null){
			m = service.getClass().getMethod(dto.getMethodName());
		}else {
			Class<?>[] clss = new Class[dto.getParameterTypeNames().length];
			for(int i = 0 ; i< clss.length; i++){
				clss[i] = Class.forName(dto.getParameterTypeNames()[i]);
			}
			m = service.getClass().getMethod(dto.getMethodName(),clss);
		}		
		return m.invoke(service, (Object[])dto.getParameter());
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
		notifier.notify(99, "unknown error : " + t.getMessage(), MessageLevel.F.ordinal());
	}	
}
