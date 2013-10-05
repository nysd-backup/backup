/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free;

import java.util.HashMap;
import java.util.Map;

/**
 * The cache of the constant to bind to SQL.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ConstantCache {

	/** the cache */
	private static final Map<String,Object> cache = new HashMap<String,Object>();
	
	/**
	 * Adds the value to cache.
	 * 
	 * @param name the name
	 * @param value the value
	 */
	public static void put(String name , Object value){
		if(cache.containsKey(name)){
			throw new IllegalArgumentException(String.format("%s is already exists",name));
		}else{
			cache.put(name, value);
		}
	}
	
	/**
	 * Gets the value.
	 * 
	 * @param name the name
	 * @return the value
	 */
	public static Object get(String name){
		return cache.get(name);
	}
	
	/**
	 * Determines whether the name exists.
	 * 
	 * @param name　the name
	 * @return true:exists
	 */
	public static boolean containsKey(String name){
		return cache.containsKey(name);
	}
	
	/**
	 * Clears the cache
	 */
	public static void destroy(){
		cache.clear();
	}
	
}
