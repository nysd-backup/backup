/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.config;

/**
 * keep alive scope.
 *
 * @author yoshida-n
 * @version	created.
 */
public enum KeepAliveScope {

	/** destroy connection per request */
	REQUEST,	

	/** connection put into thread local variable */
	THREAD
}
