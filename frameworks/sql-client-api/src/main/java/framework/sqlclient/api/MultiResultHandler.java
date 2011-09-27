/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api;


/**
 * 複数件取得時処理.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface MultiResultHandler {

	/**
	 * @param condition 検索条件
	 */
	public void handleResult(Object condition);
}
