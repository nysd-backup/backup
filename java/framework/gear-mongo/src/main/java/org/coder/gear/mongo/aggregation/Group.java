/**
 * 
 */
package org.coder.gear.mongo.aggregation;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Group operation .
 * 
 * @author yoshida-n
 *
 */
public class Group extends BasicDBObject{

	private static final long serialVersionUID = 1L;

	/**
	 * Adds the group key. 
	 * 
	 * @param items keys 
	 * @return self
	 */
	public Group id(String... items){
		DBObject object = new BasicDBObject();
		for(String item : items){
			object.put(item,"$" + item);
		}
		add("_id",object);
		return this;
	}
	
	/**
	 * Add the objects.
	 * 
	 * @param key to add
	 * @param ref to add
	 * @return self
	 */
	public Group add(String key , Object ref){
		put(key, ref);
		return this;
	}
	
	/**
	 * Add the object .
	 * 
	 * @param key to add
	 * @return self
	 */
	public Group add(String key){
		put(key, "$" + key);
		return this;
	}
	
	/**
	 * Sum operation. 
	 * 
	 * @param key to add 
	 * @return self
	 */
	public Group sum(String key){
		return add(key,new BasicDBObject("$sum",1));		
	}
	
	/**
	 * Sum operation. 
	 * 
	 * @param key to add 
	 * @param ref to add
	 * @return self
	 */
	public Group sum(String key, Object ref){
		return add(key,new BasicDBObject("$sum",ref));		
	}
	
	/**
	 * @return object
	 */
	public DBObject build(){
		return new BasicDBObject("$group", this);
	}
}
