/**
 * 
 */
package framework.sqlclient.api.orm;

/**
 * 検索条件.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public class WhereCondition {
	
	/** カラム名 */
	private final String colName; 
	
	/** バインド数 */
	private int bindCount;
	
	/** 演算子 */
	private final WhereOperand operand;
	
	/** 値 */
	private final Object value;
	
	/** betweenのto */
	private final Object toValue;

	/**
	 * @param colName カラム
	 * @param bindCount バインド数
	 * @param operand 演算子
	 * @param value 値
	 */
	public WhereCondition(String colName , int bindCount ,WhereOperand operand , Object value){
		this(colName,bindCount,operand,value,null);
	}
	
	/**
	 * @param colName カラム
	 * @param bindCount バインド数
	 * @param operand 演算子
	 * @param value 値
	 * @param toValue 値
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
