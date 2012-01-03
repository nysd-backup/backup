/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.async;

import java.lang.reflect.Method;
import java.util.concurrent.Future;

/**
 * An interface of the asynchronous proxy service.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface AsyncService {

	/**
	 * @param proxy the proxy
	 * @param method the method
	 * @param args the arguments
	 * @return the result
	 */
	public Future<Object> execute(Object proxy, Method method , Object args) throws Exception;
}
