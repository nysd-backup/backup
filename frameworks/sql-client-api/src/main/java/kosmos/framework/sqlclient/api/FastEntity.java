/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

import java.util.Map;

import kosmos.framework.bean.Pair;

/**
 * Use to access fast.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface FastEntity {
	
	/**
	 * Key = column name 
	 * value = value 
	 * @return versioning value
	 */
	Pair<String> getVersioningValue();
	
	/**HashMap
 	 * Key = column name 
	 * value = value 
	 * @return the primary keys 
	 */
	Map<String,Object> getPrimaryKeys();
	
	/**
	 * Key = column name 
	 * value = value 
	 * @return the attributes 
	 */
	Map<String,Object> getAttributes();
}
