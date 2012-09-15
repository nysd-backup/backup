/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.messaging.client;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import alpha.framework.domain.messaging.client.AbstractMessageProducer;
import alpha.framework.domain.messaging.client.JMSUtils;


/**
 * The JMS producer for Spring.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageProducerImpl extends AbstractMessageProducer{

	/** the JMS template */
	private JmsTemplate template;
	
	/**
	 * @param template the template to set
	 */
	public void setJmsTemplate(JmsTemplate template){
		this.template = template;
	}
	
	/**
	 * Gets the template.
	 * Override this method if you select the JmsTemplate by the DTO or the destination name .
	 * 
	 * @param dto the DTO
	 * @param destinationName the destinationName
	 * @return the template
	 */
	protected JmsTemplate getJmsTemplate(Serializable parameter , String destinationName){
		return template;
	}
	
	/**
	 * @see alpha.framework.domain.messaging.client.AbstractMessageProducer#invoke(alpha.domain.client.messaging.InvocationParameter, java.lang.String)
	 */
	@Override
	protected Object invoke(Serializable parameter, String destinationName)
			throws Throwable {
		
		final Serializable param = parameter;
		MessageCreator creater =new MessageCreator() {			
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createObjectMessage(param);
				JMSUtils.setPropertyAndHeader(getProperty(), message);
				return message;
			}
		};	
		
		if(destinationName == null ){
			getJmsTemplate(param,null).send(creater);
		}else{
			getJmsTemplate(param,destinationName).send(destinationName, creater);
		}
		return null;
	}

}