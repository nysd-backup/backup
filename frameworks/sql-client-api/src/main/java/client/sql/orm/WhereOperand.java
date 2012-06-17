/**
 * Copyright 2011 the original author
 */
package client.sql.orm;

/**
 * The operand.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public enum WhereOperand {
	/** ＞ */
	GreaterThan(" > "),
	/** ＜ */
	LessThan(" < "),
	/** = */
	Equal(" = "),
	/** != */
	NotEqual(" != "),
	/** ≧ */
	GreaterEqual(" >= "),
	/** ≦ */
	LessEqual(" <= "),
	/** between */
	Between(" BETWEEN "),
	/** IS NOT NULL */
	IsNotNull(" IS NOT NULL"),
	/** IS NULL */
	IsNull(" IS NULL "),
	/** IN */
	IN(" IN ");

	/** the operand */
	private String operand = null;

	/**
	 * @param operand the operand
	 */
	private WhereOperand(String operand) {
		this.operand = operand;
	}

	/**
	 * @return the operand
	 */
	public String getOperand() {
		return operand;
	}
}
