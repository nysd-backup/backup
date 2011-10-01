/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.orm;

/**
 * The condition.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class WhereCondition {
	
	/** the column name */
	private final String colName; 
	
	/** the binding value's count */
	private int bindCount;
	
	/** the operand */
	private final WhereOperand operand;
	
	/** the binding value */
	private final Object value;
	
	/** the binding value for 'between' */
	private final Object toValue;

	/**
	 * @param colName the colName
	 * @param bindCount the bindCount
	 * @param operand the operand
	 * @param value the value
	 */
	public WhereCondition(String colName , int bindCount ,WhereOperand operand , Object value){
		this(colName,bindCount,operand,value,null);
	}
	
	/**
	 * @param colName the colName
	 * @param bindCount the bindCount
	 * @param operand the operand
	 * @param value the from value
	 * @param toValue the to value 
	 */
	public WhereCondition(String colName , int bindCount ,WhereOperand operand , Object value , Object toValue){
		this.colName = colName;
		this.bindCount = bindCount;
		this.operand = operand;
		this.value = value;
		this.toValue = toValue;
	}

	/**
	 * @return the toValue
	 */
	public Object getToValue() {
		return toValue;
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
	public WhereOperand getOperand() {
		return operand;
	}

	/**
	 * @return the colName
	 */
	public String getColName() {
		return colName;
	}
	
	/**
	 * @return the colName
	 */
	public int getBindCount() {
		return bindCount;
	}
}
