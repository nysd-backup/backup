/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.strategy.impl;

import org.coder.alpha.jdbc.domain.ConstantCache;
import org.coder.alpha.jdbc.exception.QueryException;
import org.coder.alpha.jdbc.strategy.ConstantAccessor;

/**
 * Gets the constant.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ConstantAccessorImpl implements ConstantAccessor {

	/** the prefix. */
	private String constPrefix = "c_";

	/**
	 * @param constPrefix the constPrefix to set
	 */
	public final void setConstPrefix(String constPrefix) {
		this.constPrefix = constPrefix;
	}

	/**
	 * @see org.coder.alpha.jdbc.strategy.ConstantAccessor#getConstTarget(java.lang.String)
	 */
	@Override
	public Object getConstTarget(String variableName) {

		// 定数値が指定されていた場合、定数値をバインド値に追加する
		if (isValidKey(constPrefix)) {
			String fieldName = variableName.substring(constPrefix.length());
			if (ConstantCache.containsKey(fieldName)) {
				return ConstantCache.get(fieldName);
			} else {
				throw new QueryException("[Poor Implementation ] No cache was found . key = " + fieldName);
			}
		}
		throw new QueryException("[Poor Implementation ] Illegal const key . key = " + variableName);
	}

	/**
	 * @see org.coder.alpha.jdbc.strategy.ConstantAccessor#isValidKey(java.lang.String)
	 */
	@Override
	public boolean isValidKey(String key) {
		return key.startsWith(constPrefix);
	}

}
