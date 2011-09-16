/**
 * Use is subject to license terms.
 */
package framework.service.core.error;

import framework.api.dto.ClientSessionBean;
import framework.core.message.BuildedMessage;
import framework.core.message.DefinedMessage;
import framework.core.message.MessageBean;
import framework.logics.builder.MessageAccessor;
import framework.logics.builder.MessageBuilder;
import framework.service.core.transaction.ServiceContext;

/**
 * メッセージの作成、追加エンジン.
 *
 * @author	yoshida-n
 * @version	2011/02/19 new create
 */
public class MessageAccessorImpl implements MessageAccessor<MessageBean>{
	
	/** ビルダー */
	private MessageBuilder builder;
	
	/**
	 * @param builder ビルダー
	 */
	public void setMessageBuilder(MessageBuilder builder){
		this.builder = builder;
	}
	
	/**
	 * @see framework.api.message.MessageAccessor#createMesasge(int, java.lang.Object[])
	 */
	@Override
	public MessageBean createMessage(int code, Object... args) {
		return new MessageBean(code, args);
	}

	/**
	 * @see framework.api.message.MessageAccessor#addMesasge(framework.api.message.MessageBean)
	 */
	@Override
	public MessageBean addMessage(MessageBean message) {
		ServiceContext context = ServiceContext.getCurrentInstance();
		ClientSessionBean bean = context.getClientSessionBean();		
		DefinedMessage defined = builder.load(message, bean.getLocale());
		BuildedMessage builded = builder.build(defined);
		context.addMessage(builded);
		return message;
	}

}
