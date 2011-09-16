/**
 * Use is subject to license terms.
 */
package framework.sqlengine.builder;

/**
 * 定数キャッシュにアクセスしバインド値を取得する.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ConstAccessor {

	/**
	 * @param variableName バインド変数名
	 * @return 値
	 */
	public Object[] getConstTarget(String variableName);
}
