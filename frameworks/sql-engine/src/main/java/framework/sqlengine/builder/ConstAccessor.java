/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.builder;

/**
 * 定数キャッシュにアクセスしバインド値を取得する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ConstAccessor {

	/**
	 * @param variableName バインド変数名
	 * @return 値
	 */
	public Object[] getConstTarget(String variableName);
}
