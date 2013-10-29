/**
 * 
 */
package org.coder.gear.mongo.aggregation;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.sun.istack.internal.logging.Logger;

/**
 * Query API.
 * 
 * @author yoshida-n
 *
 */
public class Query {
	
	private static final Logger LOGGER = Logger.getLogger(Query.class);

	/**
	 * Target Collection.
	 */
	private final DBCollection col;
	
	
	/**
	 * Group operation.
	 */
	private Group group = new Group();
	
	/**
	 * Project operation .
	 */
	private Project project = new Project();
	
	/**
	 * Match operation.
	 */
	private Match match = new Match();
	
	/**
	 * Sort operation.
	 */
	private Sort sort = new Sort();
	
	/**
	 * Limit operation .
	 */
	private int limit = -1;
	
	/**
	 * @param limit the limit to get
	 * @return self
	 */
	public Query limit(int limit){
		this.limit = limit;
		return this;
	}
	
	/**
	 * @param col to set
	 */
	public Query(DBCollection col){
		this.col = col;
	}
	
	/**
	 * Set ascending to .   
	 * 
	 * @param key target
	 * @return self
	 */
	public Query asc(String key){
		sort.asc(key);
		return this;
	}
	
	/**
	 * Set descending to .   
	 * 
	 * @param key target
	 * @return self
	 */
	public Query desc(String key){
		sort.desc(key);
		return this;
	}
	
	/**
	 * eq operation .
	 * 
	 * @param key to set
	 * @param value to set
	 * @return self
	 */
	public Query eq(String key , Object value){
		match.eq(key, value);
		return this;
	}
	
	/**
	 * ne operation .
	 * 
	 * @param key to set
	 * @param value to set
	 * @return self
	 */
	public Query ne(String key, Object value){
		match.ne(key, value);
		return this;
	}
	
	/**
	 * gt operation .
	 * 
	 * @param key to set
	 * @param value to set
	 * @return self
	 */
	public Query gt(String key, Object value){
		match.gt(key, value);
		return this;
	}
	
	/**
	 * gte operation .
	 * 
	 * @param key to set
	 * @param value to set
	 * @return self
	 */
	public Query gte(String key, Object value){
		match.gte(key, value);
		return this;
	}
	
	/**
	 * lt operation .
	 * 
	 * @param key to set
	 * @param value to set
	 * @return self
	 */
	public Query lt(String key, Object value){
		match.lt(key, value);
		return this;
	}
	
	/**
	 * lte operation .
	 * 
	 * @param key to set
	 * @param value to set
	 * @return self
	 */
	public Query lte(String key, Object value){
		match.lte(key,value);
		return this;
	}
	
	/**
	 * Grouping key and select target .
	 * 
	 * @param keys to set
	 * @return self
	 */
	public Query keys(String... keys){
		group.id(keys);
		project.multipleInclude(keys);
		return this;
	}
	
	
	/**
	 * Summary operation .
	 * 
	 * @param key to set
	 * @return self
	 */
	public Query sum(String key){
		this.group.sum(key);
		return this;
	}
	
	/**
	 * Summary operation .
	 * 
	 * @param key to set
	 * @param object to set
	 * @return self
	 */
	public Query sum(String key,Object value){
		this.group.sum(key,value);
		return this;
	}
	
	
	/**
	 * Execute the aggregation .
	 * 
	 * @return result 
	 */
	public BasicDBList aggregate() {
		List<DBObject> option = new ArrayList<DBObject>();
		if(!match.isEmpty()){
			option.add(match.build());
		}
		if(!project.isEmpty()){
			option.add(project.build());			
		}
		if(!group.isEmpty()){
			option.add(group.build());	
		}
		if(!sort.isEmpty()){
			option.add(sort.build());				
		}
		if(limit > 0){
			option.add(new BasicDBObject("$limit",limit));
		}
		LOGGER.info(option.toString());
		
		CommandResult result = col.aggregate(option.get(0), option.subList(1, option.size()-1).toArray(new DBObject[0])).getCommandResult();
		return BasicDBList.class.cast(result.get("result"));
	}
	
}
