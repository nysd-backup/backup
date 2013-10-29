/**
 * 
 */
package org.coder.gear.mongo.aggregation;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Project operation .
 * 
 * @author yoshida-n
 *
 */
public class Project extends BasicDBObject{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Adds select target.
	 * 
	 * @param key to set
	 * @param ref to set
	 * @return self
	 */
	public Project include(String key , Object ref){
		super.put(key, ref);
		return this;
	}
	
	/**
	 * Adds select target.
	 * 
	 * @param keys to set
	 * @return self
	 */
	public Project multipleInclude(String... keys){
		for(String key : keys){
			include(key, 1);
		}
		return this;
	}

	/**
	 * @return object
	 */
	public DBObject build(){
		return new BasicDBObject("$project", this);
	}

}
