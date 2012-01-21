/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.orm;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class FixString {

	private final String value;
	
	public FixString(String value){
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String toString() {
		return value;
	}
}
