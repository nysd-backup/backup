/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import kosmos.framework.core.exception.UnexpectedMultiResultException;
import kosmos.framework.sqlclient.api.MultiResultHandler;


/**
 * Throw an <code>UnexpectedMultiResultException</code> if the multiple result are found.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class CustomMultiResultHandlerImpl implements MultiResultHandler{

	/**
	 * @see kosmos.framework.sqlclient.api.MultiResultHandler#handleResult(java.lang.Object)
	 */
	public void handleResult(Object condition){
		throw new UnexpectedMultiResultException(condition.toString());
	}
}
