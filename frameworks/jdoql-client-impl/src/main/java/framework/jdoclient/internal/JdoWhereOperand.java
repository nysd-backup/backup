/**
 * Copyright 2011 the original author
 */
package framework.jdoclient.internal;

/**
 * 演算子.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public enum JdoWhereOperand {

	/** ＞ */
	GreaterThan(" > "),
	/** ＜ */
	LessThan(" < "),
	/** = */
	Equal(" == "),
	/** != */
	NotEqual(" != "),
	/** ≧ */
	GreaterEqual(" >= "),
	/** ≦ */
	LessEqual(" <= "),
	/** contains */
	Contains("%s.contains(%s) ");

	/** 演算子 */
	private String operand = null;

	/**
	 * @param operand 演算子
	 */
	private JdoWhereOperand(String operand) {
		this.operand = operand;
	}

	/**
	 * @return 演算子
	 */
	public String getOperand() {
		return operand;
	}
}
