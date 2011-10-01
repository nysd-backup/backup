/**
 * Copyright 2011 the original author
 */
package framework.jdoclient.internal;

/**
 * The operand.
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

	/** the operand */
	private String operand = null;

	/**
	 * @param operand the operand
	 */
	private JdoWhereOperand(String operand) {
		this.operand = operand;
	}

	/**
	 * @return the operand
	 */
	public String getOperand() {
		return operand;
	}
}
