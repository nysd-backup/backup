/**
 * Copyright 2011 the original author
 */
package kosmos.framework.bean;

import java.util.Map;


/**
 * PropertyAccessor.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface PropertyAccessor<T> {
	
	T create();

	/**
	 * @param values
	 */
	void setProperties(Map<String,Object> values, T targetObject);

	/**
	 * @return
	 */
	Map<String,Pair<Class<?>>> getProperties(T targetObject);
}
