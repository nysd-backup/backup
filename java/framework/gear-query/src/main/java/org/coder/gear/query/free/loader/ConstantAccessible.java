/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.free.loader;

import org.coder.gear.query.free.ConstantCache;

/**
 * For Mixin Utility.
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface ConstantAccessible {
	
	public static final String CONST_PREFIX = "c_";
	
	/**
	 * @param variableName
	 * @return
	 */
	default Object getConstTarget(String variableName) {

		// 定数値が指定されていた場合、定数値をバインド値に追加する
		if (isValidKey(CONST_PREFIX)) {
			String fieldName = variableName.substring(CONST_PREFIX.length());
			if (ConstantCache.containsKey(fieldName)) {
				return ConstantCache.get(fieldName);
			} else {
				throw new IllegalStateException("[Poor Implementation ] No cache was found . key = " + fieldName);
			}
		}
		throw new IllegalStateException("[Poor Implementation ] Illegal const key . key = " + variableName);
	}

	/**
	 * @see org.coder.gear.query.ConstantAccessible.loader.ConstantAccessor#isValidKey(java.lang.String)
	 */
	default boolean isValidKey(String key) {
		return key.startsWith(CONST_PREFIX);
	}

}
