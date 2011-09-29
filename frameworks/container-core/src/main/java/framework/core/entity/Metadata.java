/**

 * Copyright 2011 the original author
 */
package framework.core.entity;

/**
 * A meta data of the column.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class Metadata<C,T> {
	
	/** the name of column */
	private final String name;
	
	/**
	 * @param name the name to set
	 */
	public Metadata(String name){
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String name(){
		return this.name;
	}
}
