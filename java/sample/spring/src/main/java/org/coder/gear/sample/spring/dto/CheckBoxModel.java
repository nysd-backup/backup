/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.spring.dto;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class CheckBoxModel {

	private String items = null;
	
	private boolean values = false;
	
	/**
	 * @return the items
	 */
	public String getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(String items) {
		this.items = items;
	}

	/**
	 * @return the values
	 */
	public boolean getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(boolean values) {
		this.values = values;
	}
	
}
