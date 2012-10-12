/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.activation;

import java.util.Properties;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface EJBComponentFinder extends ComponentFinder{
	
	/**
	 * @param name
	 * @param prop
	 * @return
	 */
	<T> T getBean(String name, Properties prop);
		
	/**
	 * @param requiredType
	 * @param prop
	 * @return
	 */
	<T> T getBean(Class<T> requiredType, Properties prop);
	
}
