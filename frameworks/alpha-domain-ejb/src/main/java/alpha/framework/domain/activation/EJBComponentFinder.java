/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.activation;

import java.util.Properties;

/**
 * ComponentFindeer 4 EJB.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface EJBComponentFinder extends ComponentFinder{
	
	/**
	 * Gets the bean by name.
	 * @param name the name 
	 * @param prop the property
	 * @return bean
	 */
	<T> T getBean(String name, Properties prop);
		
	/**
	 * Gets the bean by type.
	 * @param requiredType the type
	 * @param prop the property
	 * @return bean
	 */
	<T> T getBean(Class<T> requiredType, Properties prop);
	
}
