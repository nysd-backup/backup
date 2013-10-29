/**
 * 
 */
package org.coder.gear.mongo.aggregation;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Match Operation .
 * 
 * @author yoshida-n
 *
 */
public class Match extends BasicDBObject{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Eq operation .
	 * 
	 * @param key to set
	 * @param value to set
	 * @return self
	 */
	public Match eq(String key , Object value){
		super.put(key, value);
		return this;
	}
	
	/**
	 * Ne operation .
	 * 
	 * @param key to set
	 * @param value to set
	 * @return self
	 */
	public Match ne(String key, Object value){
		put(key, new BasicDBObject("$ne",value));
		return this;
	}
	
	/**
	 * Gt operation .
	 * 
	 * @param key to set
	 * @param value to set
	 * @return self
	 */
	public Match gt(String key, Object value){
		put(key, new BasicDBObject("$gt",value));
		return this;
	}
	
	/**
	 * Gte operation .
	 * 
	 * @param key to set
	 * @param value to set
	 * @return self
	 */
	public Match gte(String key, Object value){
		put(key, new BasicDBObject("$gte",value));
		return this;
	}
	
	/**
	 * Lt operation .
	 * 
	 * @param key to set
	 * @param value to set
	 * @return self
	 */
	public Match lt(String key, Object value){
		put(key, new BasicDBObject("$lt",value));
		return this;
	}
	
	/**
	 * Lte operation .
	 * 
	 * @param key to set
	 * @param value to set
	 * @return self
	 */
	public Match lte(String key, Object value){
		put(key, new BasicDBObject("$lte",value));
		return this;
	}

	
	/**
	 * @return object
	 */
	public DBObject build(){
		return new BasicDBObject("$match", this);
	}

}
