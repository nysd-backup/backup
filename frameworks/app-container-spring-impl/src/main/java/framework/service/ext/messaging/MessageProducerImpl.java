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
 * Spring用メッセージプロデューサ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageProducerImpl extends AbstractMessageProducer{

	/** JMSテンプレート */
	private JmsTemplate template;
	
	/**
	 * @param template テンプレート
	 */
	public void setJmsTemplate(JmsTemplate template){
		this.template = template;
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
			template.send(creater);
		}else{
			template.send(destinationName, creater);
		}
		return null;
	}

}
