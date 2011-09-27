/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;

/**
 * クエリモード.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public enum QueryMode {
	
	/** ResultSetをフェッチして取得 */
	Fetch,
	
	/** 実際のヒット件数取得 */
	Total
}
