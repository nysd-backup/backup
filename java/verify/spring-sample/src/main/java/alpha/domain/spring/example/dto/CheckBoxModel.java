/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package alpha.domain.spring.example.dto;


/**
 * function.
 *
 * @author yoshida-n
 * @version	1.0
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
