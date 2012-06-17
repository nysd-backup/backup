/**
 * Copyright 2011 the original author
 */
package core.base;

import java.util.List;

import core.message.MessageResult;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class AbstractResponse extends AbstractBean{

	private static final long serialVersionUID = 1L;
	
	private boolean fail;
	
	private List<MessageResult> messageResult = null;

	/**
	 * @return the messageResult
	 */
	public List<MessageResult> getMessageResult() {
		return messageResult;
	}

	/**
	 * @param messageResult the messageResult to set
	 */
	public void setMessageResult(List<MessageResult> messageResult) {
		this.messageResult = messageResult;
	}

	/**
	 * @return the fail
	 */
	public boolean isFail() {
		return fail;
	}

	/**
	 * @param fail the fail to set
	 */
	public void setFail(boolean fail) {
		this.fail = fail;
	}



}
