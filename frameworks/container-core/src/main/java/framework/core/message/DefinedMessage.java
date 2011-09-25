/**
 * Use is subject to license terms.
 */
package framework.core.message;

/**
 * メッセージ定義.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public class DefinedMessage{

	private static final long serialVersionUID = 1L;

	/** メッセージビーン */
	private MessageBean messageBean;
	/** メッセージ */
	private String message;
	/** レベル */
	private MessageLevel level;
	/** レベル */
	private boolean notify;
	
	/**
	 * @param bean メッセージビーン
	 * @param message メッセージ
	 * @param level レベル
	 * @param notify 通知有無
	 */
	public DefinedMessage(MessageBean bean ,String message ,MessageLevel level, boolean notify){
		this.messageBean = bean;
		this.message = message;
		this.level = level;
		this.notify = notify;
	}

	/**
	 * @return the level
	 */
	public MessageLevel getLevel() {
		return level;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the notify
	 */
	public boolean isNotify() {
		return notify;
	}

	/**
	 * @param messageBean the messageBean to set
	 */
	public void setMessageBean(MessageBean messageBean) {
		this.messageBean = messageBean;
	}

	/**
	 * @return the messageBean
	 */
	public MessageBean getMessageBean() {
		return messageBean;
	}
	
}
