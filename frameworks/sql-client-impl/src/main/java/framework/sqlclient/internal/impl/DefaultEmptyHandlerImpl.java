/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.internal.impl;

import framework.sqlclient.api.EmptyHandler;

/**
 * Handles the empty result.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultEmptyHandlerImpl implements EmptyHandler{

	/**
	 * @see framework.sqlclient.api.EmptyHandler#handleEmptyResult(java.lang.Object)
	 */
	@Override
	public void handleEmptyResult(Object condition) {
		throw new IllegalStateException(condition.toString());
	}

}
