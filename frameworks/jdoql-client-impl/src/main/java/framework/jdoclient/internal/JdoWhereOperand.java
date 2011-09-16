/**
 * Use is subject to license terms.
 */
package framework.jdoclient.internal;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
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
