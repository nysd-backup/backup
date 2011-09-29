/**
 * Copyright 2011 the original author
 */
package framework.core.message;

import java.io.Serializable;

/**
 * The message bean.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageBean implements Serializable{

	private static final long serialVersionUID = 7686881503687250217L;

	/** the message code  */
	private int code = -1;
	
	/** the detail */
	private Object[] detail	= null;
	
	/**
	 * @param code　the code
	 * @param args the arguments
	 */
	public MessageBean(int code , Object... args){
		this.code = code;
		this.detail = args;
	}
	
	/**
	 * get code
	 *
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * get detail
	 *
	 * @return detail
	 */
	public Object[] getDetail() {
		return detail;
	}
	
}
