/**
 * Copyright 2011 the original author
 */
package kosmos.framework.querymodel;

import kosmos.framework.core.message.MessageBean;

/**
 * Checks the result.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class Checker<T> {

	/** the message bean */
	private MessageBean messageBean;
	
	/** true: stop if process failed */
	private boolean stopIfFail;
	
	/**
	 * @param value the value
	 * @return  the result of check
	 */
	public abstract boolean check(T value);

	/**
	 * @return the messageBean
	 */
	public MessageBean getMessageBean() {
		return messageBean;
	}

	/**
	 * @param messageBean the messageBean to set
	 */
	public void setMessageBean(MessageBean messageBean) {
		this.messageBean = messageBean;
	}

	/**
	 * @return the stopIfFail
	 */
	public boolean isStopIfFail() {
		return stopIfFail;
	}

	/**
	 * @param stopIfFail the stopIfFail to set
	 */
	public void setStopIfFail(boolean stopIfFail) {
		this.stopIfFail = stopIfFail;
	}

	
}
