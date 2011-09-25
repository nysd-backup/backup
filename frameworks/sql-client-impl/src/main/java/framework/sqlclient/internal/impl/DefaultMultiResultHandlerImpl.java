/**
 * Use is subject to license terms.
 */
package framework.sqlclient.internal.impl;

import framework.sqlclient.api.MultiResultHandler;


/**
 * 複数件取得時処理.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultMultiResultHandlerImpl implements MultiResultHandler{

	/**
	 * @see framework.sqlclient.api.MultiResultHandler#handleResult(java.lang.Object)
	 */
	public void handleResult(Object condition){
		throw new IllegalStateException(condition.toString());
	}
}
