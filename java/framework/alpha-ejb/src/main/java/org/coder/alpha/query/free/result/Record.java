/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.free.result;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * One record.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class Record extends LinkedHashMap<String,Object>{

	private static final long serialVersionUID = 1L;

	/**
	 * Get value as String 
	 * @param name the name
	 * @return the value
	 */
	public String getAsString(String name){
		Object value= get(name);
		return value == null? null : value.toString();
	}
	
	/**
	 * Get value as BigDecimal
	 * @param name the name
	 * @return the value
	 */
	public BigDecimal getAsBigDecimal(String name){
		Object value= get(name);
		return value == null? null : new BigDecimal(value.toString());
	}
	
	/**
	 * Get value as Long
	 * @param name the name
	 * @return the value
	 */
	public Long getAsLong(String name){
		Object value= get(name);
		return value == null? null : new Long(value.toString());
	}
	
	/**
	 * Get value as boolean
	 * @param name the name
	 * @return the value
	 */
	public boolean getAsBoolean(String name){
		Object value= get(name);
		return value == null? false : BigDecimal.ONE.toString().equals(value.toString());
	}
	
	/**
	 * Get value as Date
	 * @param name the name
	 * @return the value
	 */
	public Date getAsDate(String name){
		Object value= get(name);
		return value == null? null : new Date(((Date)value).getTime());
	}
}
