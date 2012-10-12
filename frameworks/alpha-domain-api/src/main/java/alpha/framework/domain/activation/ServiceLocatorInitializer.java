/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.activation;

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
