/**
 * Copyright 2011 the original author
 */
package framework.api.dto;

import java.io.Serializable;

/**
 * A reply data to WEB container
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ReplyDto implements Serializable{

	private static final long serialVersionUID = -5671768150819629678L;
	
	/** the data */
	private Serializable reply;

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
