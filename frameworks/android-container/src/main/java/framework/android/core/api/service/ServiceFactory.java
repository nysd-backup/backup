/**
 * Copyright 2011 the original author
 */
package framework.android.core.api.service;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceFactory {

	public <T> T create(Class<T> service){
		if(!service.isInterface()){
			throw new IllegalArgumentException("argument must be interface");
		}
		return null;
	}
}
