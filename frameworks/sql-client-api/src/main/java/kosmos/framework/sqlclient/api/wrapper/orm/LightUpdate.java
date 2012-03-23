/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.orm;

import java.util.List;

/**
 * LightUpdate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface LightUpdate<T> extends OrmUpdateWrapper<T>{

	/**
	 * Adds the string to update.
	 * 
	 * @param setString the string
	 * @return self 
	 */
	LightUpdate<T> set(String... setString);
	
	/**
	 * Adds the filter to update.
	 * 
	 * @param filterString the filter
	 * @return self
	 */
	LightUpdate<T> filter(String filterString);
	
	/**
	 * Updates the table.
	 * 
	 * @param set the parameter to update
	 * @param params the condition to update
	 * @return the updated count 
	 */
	int execute(List<Object> set , Object... params);
	
}
