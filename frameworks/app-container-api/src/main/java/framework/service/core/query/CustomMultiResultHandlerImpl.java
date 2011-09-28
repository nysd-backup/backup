/**
 * Copyright 2011 the original author
 */
package framework.service.core.query;

import framework.core.exception.system.UnexpectedMultiResultException;
import framework.sqlclient.api.MultiResultHandler;


/**
 * Throw an <code>UnexpectedMultiResultException</code> if over one result were found.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class CustomMultiResultHandlerImpl implements MultiResultHandler{

	/**
	 * @see framework.sqlclient.api.MultiResultHandler#handleResult(java.lang.Object)
	 */
	public void handleResult(Object condition){
		throw new UnexpectedMultiResultException(condition.toString());
	}
}
