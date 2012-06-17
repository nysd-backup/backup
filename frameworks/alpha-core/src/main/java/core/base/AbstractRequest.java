/**
 * Copyright 2011 the original author
 */
package core.base;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractRequest extends AbstractBean{

	private static final long serialVersionUID = 1L;
	
	private String requestId = null;

	/**
	 * @return
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
