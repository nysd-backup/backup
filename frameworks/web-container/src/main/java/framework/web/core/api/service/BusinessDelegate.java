/**
 * Copyright 2011 the original author
 */
package framework.web.core.api.service;

import java.lang.reflect.InvocationHandler;

/**
 * サービスデリゲート.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface BusinessDelegate extends InvocationHandler{

	/**
	 * @param alias Beanのエイリアス
	 */
	public void setAlias(String alias);
	
}
