/**
 * Use is subject to license terms.
 */
package framework.sqlengine.builder.impl;

import framework.sqlengine.builder.ConstCache;
import framework.sqlengine.builder.ConstAccessor;
import framework.sqlengine.exception.SQLEngineException;

/**
 * 定数キャッシュにアクセスしバインド値を取得する.
 *
 * @author yoshida-n
 * @version created.
 */
public class ConstAccessorImpl implements ConstAccessor {

	/** プリフィクス. */
	private String constPrefix = "c_";

	/**
	 * @param constPrefix プリフィクス
	 */
	public final void setConstPrefix(String constPrefix) {
		this.constPrefix = constPrefix;
	}

	/**
	 * @see framework.sqlengine.builder.ConstAccessor#getConstTarget(java.lang.String)
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