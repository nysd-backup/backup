/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.orm;

import java.util.Map;

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
	Pair<String> toVersioningValue();
	
	/**HashMap
 	 * Key = column name 
	 * value = value 
	 * @return the primary keys 
	 */
	Map<String,Object> toPrimaryKeys();
	
	/**
	 * Key = column name 
	 * value = value 
	 * @return the attributes 
	 */
	Map<String,Object> toAttributes();
}