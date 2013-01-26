/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.config;

import org.coder.alpha.httpclient.handler.HttpCallback;

/**
 * Execution Property.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ExecutionProperty {

	/** marked as asynchronous */
	private boolean asynchronous = false;

	/** calling back method after get response */
	private HttpCallback httpCallback = null;

	/**
	 * @return the asynchronous
	 */
	public boolean isAsynchronous() {
		return asynchronous;
	}

	/**
	 * @param asynchronous the asynchronous to set
	 */
	public void setAsynchronous(boolean asynchronous) {
		this.asynchronous = asynchronous;
	}

	/**
	 * @return the httpCallback
	 */
	public HttpCallback getHttpCallback() {
		return httpCallback;
	}

	/**
	 * @param httpCallback the httpCallback to set
	 */
	public void setHttpCallback(HttpCallback httpCallback) {
		this.httpCallback = httpCallback;
	}

	
}
