/**
 * Copyright 2011 the original author
 */
package framework.service.ext.messaging;

import javax.jms.JMSException;

import framework.api.dto.RequestDto;

/**
 * JMSプロデューサ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface JmsProducer {

	/**
	 * Queueに送信。
	 * @param dto DTO
	 * @param destinationName 宛先名
	 * @throws JMSException 例外
	 */
	public void send(RequestDto dto ,String destinationName) throws JMSException;
	
	
	/**
	 * Topicに送信。
	 * @param dto DTO
	 * @param destinationName 宛先名
	 * @throws JMSException 例外
	 */
	public void publish(RequestDto dto ,String destinationName) throws JMSException;
}
