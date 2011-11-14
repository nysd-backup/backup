/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import kosmos.framework.core.exception.UnexpectedNoDataFoundException;
import kosmos.framework.sqlclient.api.EmptyHandler;

/**
 * Throw an <code>UnexpectedNoDataFoundException</code> if the no result was found.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class UnexpectedEmptyHandlerImpl implements EmptyHandler{

	/**
	 * @see kosmos.framework.sqlclient.api.EmptyHandler#handleEmptyResult(java.lang.Object)
	 */
	@Override
	public void handleEmptyResult(Object condition) {
		throw new UnexpectedNoDataFoundException(condition.toString());
	}

}
