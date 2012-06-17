/**
 * Copyright 2011 the original author
 */
package core.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A result of the messageId.
 *
 * @author yoshida-n
 * @version	created.
 */
public class MessageResult implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** client information such as component id */
	private List<ClientBean> clientBean = new ArrayList<ClientBean>();
	
	/** the message code */
	private int code;
	
	/** the message level */
	private int level;
	
	/** the message */
	private String message;
	
	/** true:notify to the fault agent */
	private boolean shouldNotify = false;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the shouldNotify
	 */
	public boolean isShouldNotify() {
		return shouldNotify;
	}

	/**
	 * @param shouldNotify the shouldNotify to set
	 */
	public void setShouldNotify(boolean shouldNotify) {
		this.shouldNotify = shouldNotify;
	}

	/**
	 * @return the clientBean
	 */
	public List<ClientBean> getClientBean() {
		return clientBean;
	}

	/**
	 * @param clientBean the clientBean to set
	 */
	public void setClientBean(List<ClientBean> clientBean) {
		this.clientBean = clientBean;
	}

}
