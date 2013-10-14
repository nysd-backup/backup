/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria;

/**
 * Fixed string for SQL.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class FixString {

	/** fix value */
	private final String value;
	
	/**
	 * @param value
	 */
	public FixString(String value){
		this.value = value;
	}

	/**
	 * @return the value to set
	 */
	public String toString() {
		return value;
	}
}
