/**
 * 
 */
package framework.api.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import framework.core.message.BuildedMessage;

/**
 * クライアントへの戻り値.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public class ReplyDto implements Serializable{

	private static final long serialVersionUID = -5671768150819629678L;
	
	/** メッセージリスト */
	private List<BuildedMessage> messageList = new ArrayList<BuildedMessage>();
	
	/** データ */
	private Serializable reply;

	/**
	 * @param messageList the messageList to set
	 */
	public void setMessageList(List<BuildedMessage> messageList) {
		this.messageList = messageList;
	}

	/**
	 * @return the messageList
	 */	
	public List<BuildedMessage> getMessageList() {
		return messageList;
	}

	/**
	 * @param reply the reply to set
	 */
	public void setReply(Serializable reply) {
		this.reply = reply;
	}

	/**
	 * @return the reply
	 */
	public Object getReply() {
		return reply;
	}

	
}
