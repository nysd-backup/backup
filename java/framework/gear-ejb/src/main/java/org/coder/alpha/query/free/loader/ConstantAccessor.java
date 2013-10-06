/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.free.loader;

/**
 * Gets the constant.
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface ConstantAccessor {
	
	/**
	 * @return true: the key is valide
	 */
	boolean isValidKey(String key);

	/**
	 * @param variableName the variableName
	 * @return the value
	 */
	Object getConstTarget(String variableName);

}
