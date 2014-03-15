/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.coder.gear.query.free.query.Conditions;



/**
 * The condition.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class Criteria {
	
	/** the column name */
	private final String colName; 
	
	/** the operand */
	private final Operand operand;
	
	/** the binding value */
	private final Object value;

	/**
	 * @param colName the colName
	 * @param bindCount the bindCount
	 * @param operand the operand
	 * @param value the from value
	 * @param toValue the to value 
	 */
	public Criteria(String colName , Operand operand , Object value){
		this.colName = colName;
		this.operand = operand;
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @return the operand
	 */
	public Operand getOperand() {
		return operand;
	}

	/**
	 * @return the colName
	 */
	public String getColName() {
		return colName;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
	/**
	 * Returns the expression
	 * @param tableAlias the alias
	 * @return expression
	 */
	public String getExpression( int bindCount){
		String name = "e." +colName;
		return operand.getExpression(name, colName+"_" + bindCount, value);
	}
	
	/**
	 * Sets the parameter 
	 * @param tableAlias the alias
	 * @return expression
	 */
	public void accept(Conditions conditions, int index){
		conditions.getParam().put(getExpression(index), value);
	}
}
