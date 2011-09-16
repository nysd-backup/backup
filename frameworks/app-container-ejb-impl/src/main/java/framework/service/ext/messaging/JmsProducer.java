/**
 * Use is subject to license terms.
 */
package framework.service.ext.messaging;

import javax.jms.JMSException;

import framework.api.dto.RequestDto;

/**
 * メッセージ送信.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface JmsProducer {

	/**
	 * メッセージ送信
	 * @param dto
	 * @param destinationName
	 */
	public void send(RequestDto dto ,String destinationName) throws JMSException;
	
	
	/**
	 * メッセージ送信
	 * @param dto
	 * @param destinationName
	 */
	public void publish(RequestDto dto ,String destinationName) throws JMSException;
}
