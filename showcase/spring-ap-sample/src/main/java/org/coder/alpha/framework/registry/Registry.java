/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.registry;

import org.springframework.context.ApplicationContext;


/**
 * The alpha.domain locator using Spring
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class Registry {
	
	/** the context */
	private static ApplicationContext context;

	/**
	 * @param context
	 */
	public static void init(ApplicationContext appContext){
		context = appContext;
	}
	
	/**
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getService(String name) {
		return (T) context.getBean(name);
	}
	
	/**
	 * @param requiredType
	 * @return
	 */
	public static <T> T getService(Class<T> requiredType) {
		return context.getBean(requiredType);
	}


}
