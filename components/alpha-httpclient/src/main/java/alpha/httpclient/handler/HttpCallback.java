/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.handler;

import org.apache.http.HttpResponse;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface HttpCallback {

	public void callback(HttpResponse response);
}
