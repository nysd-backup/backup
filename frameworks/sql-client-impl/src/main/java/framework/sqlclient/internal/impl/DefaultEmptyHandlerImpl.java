/**
 * Use is subject to license terms.
 */
package framework.sqlclient.internal.impl;

import framework.sqlclient.api.EmptyHandler;

/**
 * 0件時処理.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultEmptyHandlerImpl implements EmptyHandler{

	/**
	 * @see framework.sqlclient.api.free.EmptyHandler#handleEmptyResult(java.lang.Object)
	 */
	@Override
	public void handleEmptyResult(Object condition) {
		throw new IllegalStateException(condition.toString());
	}

}
