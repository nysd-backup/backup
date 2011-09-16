/**
 * Use is subject to license terms.
 */
package framework.service.core.query;

import framework.core.exception.system.UnexpectedNoDataFoundException;
import framework.sqlclient.api.EmptyHandler;

/**
 * 0件時処理.
 *
 * @author yoshida-n
 * @version	2011/04/09 created.
 */
public class CustomEmptyHandlerImpl implements EmptyHandler{

	/**
	 * @see framework.sqlclient.api.EmptyHandler#handleEmptyResult(java.lang.Object)
	 */
	@Override
	public void handleEmptyResult(Object condition) {
		throw new UnexpectedNoDataFoundException(condition.toString());
	}

}
