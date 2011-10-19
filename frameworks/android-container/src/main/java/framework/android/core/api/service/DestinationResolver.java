/**
 * Copyright 2011 the original author
 */
package framework.android.core.api.service;

import java.lang.reflect.Method;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface DestinationResolver {

	/**
	 * @param method
	 * @return
	 */
	public String resolveUrl(Method method);
}
