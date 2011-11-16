/**
 * Copyright 2011 the original author
 */
package kosmos.framework.client.core.api.service;

import java.lang.reflect.InvocationHandler;

/**
 * the BusinessDelegate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface BusinessDelegate extends InvocationHandler{

	/**
	 * @param alias the alias of the bean
	 */
	public void setAlias(String alias);
	
}
