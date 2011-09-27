/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.orm;

/**
 * 演算子.
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

	/** 演算子 */
	private String operand = null;

	/**
	 * @param operand 演算子
	 */
	private WhereOperand(String operand) {
		this.operand = operand;
	}

	/**
	 * @return 演算子
	 */
	public String getOperand() {
		return operand;
	}
}
