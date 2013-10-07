/**
 * 
 */
package org.coder.gear.query.criteria.mongo;

import java.io.Serializable;

/**
 * @author yoshida-n
 *
 */
public class OrderLine implements Serializable {
    private int lineNumber;
    private String description;
    private double cost = 0;
    
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
    
    
    
}
