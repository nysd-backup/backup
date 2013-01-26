/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.handler;

import org.apache.http.HttpResponse;

/**
 * HttpCallback.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface HttpCallback {

	/**
	 * Calls back when receive the response. 
	 * @param response
	 */
	public void callback(HttpResponse response);

}
