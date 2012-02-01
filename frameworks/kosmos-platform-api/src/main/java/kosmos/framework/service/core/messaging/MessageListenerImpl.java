/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.messaging;

import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import kosmos.framework.core.activation.ServiceActivator;
import kosmos.framework.core.context.MessageContext;
import kosmos.framework.core.dto.CompositeRequest;
import kosmos.framework.core.logics.log.FaultNotifier;
import kosmos.framework.core.message.MessageLevel;
import kosmos.framework.core.message.MessageResult;
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
		
		FaultNotifier notifier = ServiceLocator.createDefaultFaultNotifier();
		
		CompositeRequest dto  = null;
		try{
			ObjectMessage message = ObjectMessage.class.cast(arg0);		
			dto = CompositeRequest.class.cast( message.getObject());
		}catch(Throwable jmse){
			notifyException(jmse,notifier);
			throw new IllegalStateException(jmse);
		}
		MessageContext context = new MessageContext();		
		context.initialize();			
		try{
			Throwable t = null;
			try {
				ServiceActivator activator = ServiceLocator.createDefaultServiceActivator();
				activator.activate(dto);			
			} catch (Throwable e) {
				t = e;
			}
			if(t != null){
				handleThrowable(t,notifier);
			}else{
				afterComplete(notifier);
			}
		}finally{
			context.release();
		}
	}
	
	/**
	 * Handles the exceptions and errors.
	 * @param notifier the notifier
	 * @param e the exception
	 */
	protected void handleThrowable(Throwable e,FaultNotifier notifier){
		notifyException(e,notifier);
		notifyMessages(notifier);		
		if(e instanceof Error){
			throw Error.class.cast(e);
		}else if(e instanceof RuntimeException){
			throw RuntimeException.class.cast(e);
		}
		throw new IllegalStateException(e);
	}
	
	/**
	 * After completion.
	 * @param notifier the notifier
	 */
	protected void afterComplete(FaultNotifier notifier){
		notifyMessages(notifier);
	}
	
	/**
	 * Notifies exception to the agent.
	 * @param t the exception 
	 * @param notifier the notifier
	 */
	protected void notifyException(Throwable t ,FaultNotifier notifier){		
		notifier.notify(99, "unknown error : " + t.getMessage(), MessageLevel.F.ordinal());
	}	
	
	/**
	 * Notifies messages to the agent.
	 * @param notifier the notifier
	 */
	private void notifyMessages(FaultNotifier notifier){
		//業務エラーの障害通知		
		List<MessageResult> messages = MessageContext.getCurrentInstance().getMessageList();
		for(MessageResult r: messages){
			if(r.isShouldNotify()){
				notifier.notify(r.getCode(), r.getMessage(), r.getLevel());
			}
		}
		
	}
}
