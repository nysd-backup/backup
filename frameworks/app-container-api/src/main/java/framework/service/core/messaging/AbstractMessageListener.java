/**
 * Use is subject to license terms.
 */
package framework.service.core.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import framework.api.dto.RequestDto;
import framework.api.service.RequestListener;

/**
 * メッセージリスナー.
 *
 * @author	yoshida-n
 * @version	2010/12/30 new create
 */
public abstract class AbstractMessageListener implements MessageListener{

	/**
	 * @param arg0 メッセージ
	 */
	public void onMessage(Message arg0) {

		ObjectMessage message = ObjectMessage.class.cast(arg0);
		RequestDto dto  = null;
		try{
			dto = RequestDto.class.cast( message.getObject());
		}catch(JMSException jmse){
			throw new IllegalStateException(jmse);
		}
		RequestListener listener = createListener();
		listener.processService(dto);
	}
	
	/**
	 * @return リクエストリスナー
	 */
	protected abstract RequestListener createListener();
}
