/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.builder;

/**
 * Gets the constant.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ConstAccessor {

	/**
	 * @param variableName the variableName
	 * @return the value
	 */
	public Object[] getConstTarget(String variableName);
}
