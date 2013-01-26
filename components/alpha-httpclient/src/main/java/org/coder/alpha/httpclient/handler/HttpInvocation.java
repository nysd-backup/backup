/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.handler;

import java.lang.reflect.Method;

import org.coder.alpha.httpclient.config.RequestProperty;


/**
 * HttpInvocation.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface HttpInvocation {

	/**
	 * Execute http request
	 * @param property
	 * @param method
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object request(RequestProperty property , Method method,Object[] args) throws Exception;
}
