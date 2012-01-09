/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import kosmos.framework.core.dto.CompositeRequest;
import kosmos.framework.service.core.messaging.AbstractMessageProducer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


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
	protected JmsTemplate getJmsTemplate(CompositeRequest dto , String destinationName){
		return template;
	}
	
	/**
	 * @see kosmos.framework.service.core.messaging.AbstractMessageProducer#invoke(kosmos.framework.core.dto.CompositeRequest, java.lang.String)
	 */
	@Override
	protected Object invoke(CompositeRequest dto, String destinationName)
			throws Throwable {
		
		final CompositeRequest req = dto;
		MessageCreator creater =new MessageCreator() {			
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(req);
				
			}
		};	
		
		if(destinationName == null ){
			getJmsTemplate(dto,null).send(creater);
		}else{
			getJmsTemplate(dto,destinationName).send(destinationName, creater);
		}
		return null;
	}

}
