/**
 * 
 */
package org.coder.gear.mongo.aggregation;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Sort operation .
 * 
 * @author yoshida-n
 *
 */
public class Sort extends BasicDBObject{

	private static final long serialVersionUID = 1L;

	/**
	 * Desc operation .
	 * 
	 * @param key to set
	 * @return self
	 */
	public Sort desc(String key ){
		put(key, -1);
		return this;
	}
	
	/**
	 * Asc operation 
	 * @param key to set
	 * @return eslf
	 */
	public Sort asc(String key){
		put(key, 1);
		return this;
	}
	
	/**
	 * @return object
	 */
	public DBObject build(){
		return new BasicDBObject("$sort", this);
	}
}
