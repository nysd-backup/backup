/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query;

import java.util.List;

/**
 * EasyUpdate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface EasyUpdate<T> extends LimitedOrmUpdate<T>{

	/**
	 * Adds the string to update.
	 * 
	 * @param setString the string
	 * @return self 
	 */
	public EasyUpdate<T> set(String... setString);
	
	/**
	 * Adds the filter to update.
	 * 
	 * @param filterString the filter
	 * @return self
	 */
	public EasyUpdate<T> filter(String filterString);
	
	/**
	 * Updates the table.
	 * 
	 * @param set the parameter to update
	 * @param params the condition to update
	 * @return the updated count 
	 */
	public int execute(List<Object> set , Object... params);
	
}
