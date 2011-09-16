/**
 * Use is subject to license terms.
 */
package framework.service.ext.messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import framework.api.dto.RequestDto;

/**
 * JmsProducer.
 * 
 * <pre>
 * コネクションの取得とメッセージ送信を行う。
 * 宛先作成、コネクションファクトリ作成用にSessionBeanを作成すること。
 * また、XAコネクションが生成できるコネクションファクトリとすること。
 * </pre>
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractJmsProducer implements JmsProducer {

	/**
	 * @see framework.service.ext.messaging.JmsProducer#send(framework.api.dto.RequestDto, java.lang.String)
	 */
	@Override
	public void send(RequestDto dto, String destinationName) throws JMSException{
		ConnectionFactory factory = createQueueConnectionFactory();
		sendMessage(factory,dto,destinationName);
	}

	/**
	 * @see framework.service.ext.messaging.JmsProducer#publish(framework.api.dto.RequestDto, java.lang.String)
	 */
	@Override
	public void publish(RequestDto dto, String destinationName)  throws JMSException{
		ConnectionFactory factory = createTopicConnectionFactory();
		sendMessage(factory,dto,destinationName);
	}
	
	/**
	 * メッセージ送信
	 * @param factory XAコネクションファクトリ
	 * @param dto
	 * @param destinationName
	 * @throws JMSException
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
	 * 宛先の作成
	 * @param dto
	 * @param destinationName
	 * @return
	 */
	protected abstract Destination createDestination(String destinationName);	

	/**
	 * @return キュー用XAコネクションの作成
	 */
	protected abstract ConnectionFactory createQueueConnectionFactory();
	
	/**
	 * @return トピック用XAコネクションの作成
	 */
	protected abstract ConnectionFactory createTopicConnectionFactory();	
	
}
