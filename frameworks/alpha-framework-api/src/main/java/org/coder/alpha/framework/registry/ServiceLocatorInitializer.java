/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.registry;

/**
 * ServiceLocatorInitializer.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ServiceLocatorInitializer {

	/**
	 * @param componentFinder
	 */
	public void initiazie(ComponentFinder componentFinder){
		ServiceLocator.setComponentFinder(componentFinder);
	}
}
