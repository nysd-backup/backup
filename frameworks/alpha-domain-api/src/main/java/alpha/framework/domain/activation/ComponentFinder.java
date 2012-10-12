/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.activation;

/**
 * Strategy of the locator.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ComponentFinder {

	/**
	 * Gets the bean by specified class
	 * @param name the name
	 * @return bean
	 */
	public <T> T getBean(String name);
	
	/**
	 * Gets the bean by specified class
	 * @param requiredType the requiredType
	 * @return bean
	 */
	public <T> T getBean(Class<T> requiredType);
	
}
