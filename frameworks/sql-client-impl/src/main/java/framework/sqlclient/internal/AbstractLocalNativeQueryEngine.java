/**
 * Use is subject to license terms.
 */
package framework.sqlclient.internal;

import framework.sqlclient.api.free.NativeQuery;

/**
 *　NativeQueryの実行エンジン
 *
 * @author	yoshida-n
 * @version	created.
 */
public abstract class AbstractLocalNativeQueryEngine<T extends AbstractInternalQuery> extends AbstractLocalQueryEngine<T> implements NativeQuery{

	/**
	 * @param delegate　クエリ
	 */
	public AbstractLocalNativeQueryEngine(T delegate){
		super(delegate);
	}
	
}
