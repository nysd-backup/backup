/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.builder.impl;

import kosmos.framework.sqlengine.builder.ConstAccessor;
import kosmos.framework.sqlengine.builder.ConstCache;
import kosmos.framework.sqlengine.exception.SQLEngineException;

/**
 * Gets the constant.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ConstAccessorImpl implements ConstAccessor {

	/** the prefix. */
	private String constPrefix = "c_";

	/**
	 * @param constPrefix the constPrefix to set
	 */
	public final void setConstPrefix(String constPrefix) {
		this.constPrefix = constPrefix;
	}

	/**
	 * @see kosmos.framework.sqlengine.builder.ConstAccessor#getConstTarget(java.lang.String)
	 */
	@Override
	public Object[] getConstTarget(String variableName) {

		// 定数値が指定されていた場合、定数値をバインド値に追加する
		if (variableName.startsWith(constPrefix)) {
			String fieldName = variableName.substring(constPrefix.length());
			if (ConstCache.containsKey(fieldName)) {
				Object[] value = new Object[1];
				value[0] = ConstCache.get(fieldName);
				return value;
			} else {
				throw new SQLEngineException("[Poor Implementation ] No cache was found . key = " + fieldName);
			}
		}
		return new Object[0];
	}

}
