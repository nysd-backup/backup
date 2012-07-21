/**
 * Copyright 2011 the original author
 */
package service.client.messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * The low level API for JMS.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class JmsUtils {

	/**
	 * Send the message.
	 * @param factory the XA connection factory.
	 * @param dto the DTO
	 * @param destinationName the name of destination
	 * @throws JMSException the exception
	 */
	public static void sendMessage(ConnectionFactory factory ,InvocationParameter dto, Destination destination) throws JMSException{
		Connection connection = null;
		Session session = null;		
		MessageProducer sender = null;
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
	
}
