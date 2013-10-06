/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.free.loader;

import org.coder.alpha.query.free.ConstantCache;

/**
 * Gets the constant.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class DefaultConstantAccessor implements ConstantAccessor {

	/** the prefix. */
	private String constPrefix = "c_";

	/**
	 * @param constPrefix the constPrefix to set
	 */
	public final void setConstPrefix(String constPrefix) {
		this.constPrefix = constPrefix;
	}

	/**
	 * @see org.coder.alpha.query.free.loader.ConstantAccessor#getConstTarget(java.lang.String)
	 */
	@Override
	public Object getConstTarget(String variableName) {

		// 定数値が指定されていた場合、定数値をバインド値に追加する
		if (isValidKey(constPrefix)) {
			String fieldName = variableName.substring(constPrefix.length());
			if (ConstantCache.containsKey(fieldName)) {
				return ConstantCache.get(fieldName);
			} else {
				throw new IllegalStateException("[Poor Implementation ] No cache was found . key = " + fieldName);
			}
		}
		throw new IllegalStateException("[Poor Implementation ] Illegal const key . key = " + variableName);
	}

	/**
	 * @see org.coder.alpha.query.free.loader.ConstantAccessor#isValidKey(java.lang.String)
	 */
	@Override
	public boolean isValidKey(String key) {
		return key.startsWith(constPrefix);
	}

}
