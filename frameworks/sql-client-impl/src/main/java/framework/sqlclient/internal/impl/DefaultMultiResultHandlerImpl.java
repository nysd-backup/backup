/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.internal.impl;

import framework.sqlclient.api.MultiResultHandler;


/**
 * 複数件取得時処理.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultMultiResultHandlerImpl implements MultiResultHandler{

	/**
	 * @see framework.sqlclient.api.MultiResultHandler#handleResult(java.lang.Object)
	 */
	public void handleResult(Object condition){
		throw new IllegalStateException(condition.toString());
	}
}
