/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.impl;

import kosmos.framework.sqlclient.api.MultiResultHandler;


/**
 * Handles the multiple result.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultMultiResultHandlerImpl implements MultiResultHandler{

	/**
	 * @see kosmos.framework.sqlclient.api.MultiResultHandler#handleResult(java.lang.Object)
	 */
	public void handleResult(Object condition){
		throw new IllegalStateException(condition.toString());
	}
}
