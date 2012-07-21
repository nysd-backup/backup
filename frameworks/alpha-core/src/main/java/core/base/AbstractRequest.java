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
	
	private int invocationSource = -1;

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

	/**
	 * @return the invocationSource
	 */
	public int getInvocationSource() {
		return invocationSource;
	}

	/**
	 * @param invocationSource the invocationSource to set
	 */
	public void setInvocationSource(int invocationSource) {
		this.invocationSource = invocationSource;
	}

}
