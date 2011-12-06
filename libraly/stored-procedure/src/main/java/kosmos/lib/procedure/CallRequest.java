/**
 * Copyright 2011 the original author
 */
package kosmos.lib.procedure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The parameter for stored procedure.
 * 
 * @author yoshida-n
 * @version 2010/10/29 created
 */
public class CallRequest {

	public static enum InOutType {
		In,
		Out,
		InOut
	}

	public static enum PlSqlDataType {

		/** パラメータデータタイプ：数値 */
		Number,
		/** パラメータデータタイプ：文字列（固定） */
		Char,
		/** パラメータデータタイプ：文字列（可変） */
		Varchar,
		/** パラメータデータタイプ：数値配列 */
		NumberArray,
		/** パラメータデータタイプ：文字列（可変）配列 */
		VarcharArray,
		/** パラメータデータタイプ：日付 */
		Date;
	}

	public static class Bind {
		public String name;
		public Object value;
		public PlSqlDataType type;
		public InOutType inout;
	}

	/** the parameter */
	private List<Bind> parameter = null;

	/** the sql string */
	private final String callString;

	/** the timeout */
	private int queryTimeout = 0;
	
	/**
	 * @param callString the callString to set
	 */
	public CallRequest(String callString) {
		parameter = new ArrayList<Bind>();
		this.callString = callString;
	}

	/**
	 * Add the IN parameter.
	 * 
	 * @param value the value
	 * @return self
	 */
	public CallRequest add(Object value) {
		Bind param = new Bind();
		param.inout = InOutType.In;
		setValue(param, value, param.inout);
		parameter.add(param);
		return this;
	}

	/**
	 * Adds the OUT parameter.
	 * 
	 * <pre>
	 *  CallRequest param = createCall(callStrning);
	 *  param.add("100").addOut("out1",PlSqlDataType.Varchar).add("out2",PlSqlDataType.Varchar);
	 *  CallResult callResult = executeProcedure(call);
	 *  
	 *  String out1 = callResult.getResultData().getString("out1");
	 *  String out2 = callResult.getResultData().getString("out2");
	 * 
	 * </pre>
	 * 
	 * @param name the name
	 * @param dataType the dataType
	 * @return self
	 */
	public CallRequest addOut(String name, PlSqlDataType dataType) {
		Bind param = new Bind();
		param.name = name;
		param.value = null;
		param.type = dataType;
		param.inout = InOutType.Out;
		parameter.add(param);
		return this;
	}

	/**
	 * Set the value.
	 * 
	 * @param param the bind parameter
	 * @param value the value
	 * @param inout the type
	 */
	private void setValue(Bind param, Object value, InOutType inout) {

		if (value == null && InOutType.Out != inout) {
			throw new IllegalArgumentException("null value is not allowed in 'In/InOut' parameter");
		}

		// 文字列配列の場合、最大文字列長計算
		if (value instanceof String[]) {
			param.type = PlSqlDataType.VarcharArray;
			param.value = value;
			
		} else if (value instanceof Long[] || value instanceof BigDecimal[]) {
			param.type = PlSqlDataType.NumberArray;
			param.value = value;
			
		} else if (value.getClass().isArray()) {
			throw new IllegalArgumentException("invalid array type : value = " + value);

			// 日付時刻
		} else if (value instanceof java.util.Date) {
			java.util.Date date = (java.util.Date) value;
			param.value = new java.sql.Timestamp(date.getTime());
			param.type = PlSqlDataType.Date;

			// 数値
		} else if (value instanceof BigDecimal || value instanceof Long || value instanceof Integer) {
			param.value = value;
			param.type = PlSqlDataType.Number;

			// 文字列
		} else if (value instanceof String) {
			param.value = value;
			param.type = PlSqlDataType.Varchar;

			// Boolean
		} else if (value instanceof Boolean) {
			param.value = value.toString();
			param.type = PlSqlDataType.Varchar;

			// カーソル等禁止
		} else {
			throw new IllegalArgumentException(String.format("Invalid Type value = %s type = %s name = %s", value, value.getClass(), param.name));
		}

	}

	/**
	 * @return callString 
	 */
	public String getCallString() {
		return callString;
	}

	/**
	 * @return the parameter list
	 */
	public List<Bind> getParameter() {
		return this.parameter;
	}

	/**
	 * @return the queryTimeout
	 */
	public int getQueryTimeout() {
		return queryTimeout;
	}

	/**
	 * @param queryTimeout the queryTimeout to set
	 * @return self
	 */
	public CallRequest setQueryTimeout(int queryTimeout) {
		this.queryTimeout = queryTimeout;
		return this;
	}

}
