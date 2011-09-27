/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api;


/**
 * 検索結果0件時の処理.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface EmptyHandler {

	/**
	 * @param condition 検索条件
	 */
	public void handleEmptyResult(Object condition);
}
