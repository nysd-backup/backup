/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.handler;

import java.lang.reflect.Method;

import alpha.httpclient.config.RequestProperty;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface HttpInvocation {

	public Object request(RequestProperty property , Method method,Object[] args) throws Exception;
}
