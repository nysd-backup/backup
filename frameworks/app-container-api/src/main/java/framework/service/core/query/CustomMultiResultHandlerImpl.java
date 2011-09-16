/**
 * Use is subject to license terms.
 */
package framework.service.core.query;

import framework.core.exception.system.UnexpectedMultiResultException;
import framework.sqlclient.api.MultiResultHandler;


/**
 * 複数件取得時処理.
 *
 * @author yoshida-n
 * @version	2011/02/26 created.
 */
public class CustomMultiResultHandlerImpl implements MultiResultHandler{

	/**
	 * @param condition 検索条件
	 */
	public void handleResult(Object condition){
		throw new UnexpectedMultiResultException(condition.toString());
	}
}
