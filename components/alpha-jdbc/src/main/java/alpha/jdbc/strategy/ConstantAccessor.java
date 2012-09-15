/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.strategy;

/**
 * Gets the constant.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ConstantAccessor {

	/**
	 * @param variableName the variableName
	 * @return the value
	 */
	Object[] getConstTarget(String variableName);
}
