/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import kosmos.framework.api.dto.RequestDto;


/**
 * The JMS producer.
 * 
 * <pre>
 * Connect server and send the message.
 * <code>ConnectionFactory</code> must be XA connection factory.
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractJmsProducer implements JmsProducer {

	/**
	 * @see kosmos.framework.service.core.messaging.JmsProducer#send(kosmos.framework.api.dto.RequestDto, java.lang.String)
	 */
	@Override
	public void send(RequestDto dto, String destinationName) throws JMSException{
		ConnectionFactory factory = createQueueConnectionFactory(dto , destinationName);
		sendMessage(factory,dto,destinationName);
	}

	/**
	 * @see kosmos.framework.service.core.messaging.JmsProducer#publish(kosmos.framework.api.dto.RequestDto, java.lang.String)
	 */
	@Override
	public void publish(RequestDto dto, String destinationName)  throws JMSException{
		ConnectionFactory factory = createTopicConnectionFactory(dto , destinationName);
		sendMessage(factory,dto,destinationName);
	}
	
	/**
	 * Send the message.
	 * @param factory the XA connection factory.
	 * @param dto the DTO
	 * @param destinationName the name of destination
	 * @throws JMSException the exception
	 */
	protected void sendMessage(ConnectionFactory factory ,RequestDto dto, String destinationName) throws JMSException{
		Connection connection = null;
		Session session = null;		
		MessageProducer sender = null;
		Destination destination = createDestination(destinationName);
		try {
			// コネクションを作成
			connection = factory.createConnection();

			// セッションを作成
			// グローバルトランザクション固定
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// MessageProducerを作成
			sender = session.createProducer(destination);
			// メッセージを作成
			ObjectMessage message = session.createObjectMessage(dto);
			connection.start();
			sender.send(message);
		} finally {
			// 接続を切断
			if (sender != null) {
				sender.close();
			}
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				//XAコネクションファクトリ経由のコネクションであればクローズ可能
				connection.close();
			}
			
		}
	}
	
	
	/**
	 * Creates the destination.
	 * 
	 * @param destinationName the name of destination
	 * @return the destination
	 */
	protected abstract Destination createDestination(String destinationName);	

	/**
	 * @param dto the DTO
	 * @param destinationName the name of the destination
	 * @return the XA connection factory for QUEUE
	 */
	protected abstract ConnectionFactory createQueueConnectionFactory(RequestDto dto ,String destinationName);
	
	/**
 	 * @param dto the DTO
	 * @param destinationName the name of the destination
	 * @return the XA connection factory for TOPIC
	 */
	protected abstract ConnectionFactory createTopicConnectionFactory(RequestDto dto ,String destinationName);
	
}
