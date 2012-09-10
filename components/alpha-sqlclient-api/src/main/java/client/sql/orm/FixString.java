/**
 * Copyright 2011 the original author
 */
package client.sql.orm;

/**
 * Fixed string for SQL.
 *
 * @author yoshida-n
 * @version	created.
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
