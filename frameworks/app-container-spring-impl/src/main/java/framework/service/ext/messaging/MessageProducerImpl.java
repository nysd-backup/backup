/**
 * Copyright 2011 the original author
 */
package framework.service.ext.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import framework.api.dto.RequestDto;
import framework.service.core.messaging.AbstractMessageProducer;

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
	protected JmsTemplate getJmsTemplate(RequestDto dto , String destinationName){
		return template;
	}
	
	/**
	 * @see framework.service.core.messaging.AbstractMessageProducer#invoke(framework.api.dto.RequestDto, java.lang.String)
	 */
	@Override
	protected Object invoke(RequestDto dto, String destinationName)
			throws Throwable {
		
		final RequestDto req = dto;
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
