/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.orm;

/**
 * Key and value.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Pair<K> {

	/** the key */
	private final K key;
	
	/** the value */
	private final Object value;
	
	/**
	 * @param key the key to set
	 * @param value the value to set
	 */
	public Pair(K key , Object value){
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	
}
